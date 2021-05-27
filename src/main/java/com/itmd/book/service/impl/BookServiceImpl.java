package com.itmd.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itmd.book.MQ.SendMessage;
import com.itmd.book.mapper.BookMapper;
import com.itmd.book.mapper.ClassificationMapper;
import com.itmd.book.mapper.PressMapper;
import com.itmd.book.service.BookService;
import com.itmd.book.vi.OrderData;
import com.itmd.enums.ExceptionEnum;
import com.itmd.exception.RaoraoBookShopException;
import com.itmd.pojo.Book;
import com.itmd.pojo.BookClassification;
import com.itmd.pojo.BookPress;
import com.itmd.vo.BookLw;
import com.itmd.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ClassificationMapper classificationMapper;
    @Autowired
    private PressMapper pressMapper;
    @Autowired
    private SendMessage sendMessage;

    @Override
    @Transactional
    public void addBookInfo(Book book) {
        int insert = bookMapper.insert(book);
        if(insert != 1){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_SAVE_ERROR);
        }
        insert = bookMapper.insertBookStock(book.getId(), book.getInventory());
        if(insert != 1 ){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_SAVE_ERROR);
        }
        addBookClassification(book);
        sendMessage.sendMQ(book.getId(), "insert");
    }

    @Override
    public PageResult<BookLw> queryBookInfoByPage(Integer page, Integer rows, String key, Boolean saleable) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            //过滤条件
            criteria.andLike("name", "%"+key+"%");
        }
        if(saleable != null){
            //过滤条件
            criteria.andEqualTo("saleable",saleable);
        }
        //查询
        List<Book> books= bookMapper.selectByExample(example);
        //异常检测
        if(CollectionUtils.isEmpty(books)){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_NOT_FOUND);
        }
        //List<Book>转List<BookLw>  JDK8新特性 stream.map.collect
        List<BookLw> bookLws = books.stream().map(book -> {
            BookLw bookLw = new BookLw();
            //拷贝属性
            BeanUtils.copyProperties(book, bookLw);
            //查询图书直接对应的父分类
            List<BookClassification> bookClassifications = classificationMapper.queryClassificationByBid(book.getId());
            if(CollectionUtils.isEmpty(bookClassifications)){
                throw new RaoraoBookShopException(ExceptionEnum.BOOK_NOT_FOUND);
            }
            List<String> names = bookClassifications.stream().map(b -> b.getCateName()).collect(Collectors.toList());
            bookLw.setClassificationName(StringUtils.join(names, " "));
            //查询出版社
            BookPress bookPress = pressMapper.selectByPrimaryKey(book.getPress());
            if(bookPress == null){
                throw new RaoraoBookShopException(ExceptionEnum.BOOK_NOT_FOUND);
            }
            bookLw.setPressName(bookPress.getName());
            return bookLw;
        }).collect(Collectors.toList());
        //解析分页结果
        PageInfo<Book> info = new PageInfo<>(books);

        return new PageResult<BookLw>(info.getTotal(),bookLws);
    }

    @Override
    @Transactional
    public void updateBookInfoById(Book book) {
        int status = bookMapper.updateByPrimaryKey(book);
        if(status != 1){
            throw  new RaoraoBookShopException(ExceptionEnum.BOOK_UPDATE_ERROR);
        }
        status = bookMapper.updateBookStockById(book.getId(), book.getInventory());
        if(status != 1){
            throw  new RaoraoBookShopException(ExceptionEnum.BOOK_UPDATE_ERROR);
        }
        status = bookMapper.deleteBookClassificationMid(book.getId());
        if(status < 1){
            throw  new RaoraoBookShopException(ExceptionEnum.BOOK_UPDATE_ERROR);
        }
        addBookClassification(book);
        sendMessage.sendMQ(book.getId(), "update");
    }

    @Override
    public void updateBookInfoById(Long id,Boolean saleable) {
        int status = bookMapper.updateBookSaleableById(id, saleable);
        if(status != 1){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_UPDATE_ERROR);
        }
        sendMessage.sendMQ(id, "update");
    }

    @Override
    @Transactional
    public void addBookClassification(Book book) {
        List<Long> classifications = book.getClassifications();
        if(CollectionUtils.isEmpty(classifications)) {
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_MID_SAVE_ERROR);
        }
        int status = 0;
        for (Long classification : classifications) {
             status = bookMapper.insertBookClassificationMid(classification, book.getId());
            if (status != 1) {
                throw new RaoraoBookShopException(ExceptionEnum.BOOK_MID_SAVE_ERROR);
            }
        }
    }

    @Override
    public BookLw queryBookById(Long id) {
        Book book = bookMapper.selectByPrimaryKey(id);
        if(book == null){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_NOT_FOUND);
        }
        BookLw bookLw = new BookLw();
        //拷贝属性
        BeanUtils.copyProperties(book, bookLw);
        //查询图书直接对应的父分类
        List<BookClassification> bookClassifications = classificationMapper.queryClassificationByBid(book.getId());
        if(CollectionUtils.isEmpty(bookClassifications)){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_NOT_FOUND);
        }
        List<String> names = bookClassifications.stream().map(b -> b.getCateName()).collect(Collectors.toList());
        bookLw.setClassificationName(StringUtils.join(names, " "));
        //查询出版社
        BookPress bookPress = pressMapper.selectByPrimaryKey(book.getPress());
        if(bookPress == null){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_NOT_FOUND);
        }
        bookLw.setPressName(bookPress.getName());
        return bookLw;
    }

    @Override
    public Integer queryBookStockById(Long id,int type) {
        int stock = bookMapper.selectBookStockById(id);
        return type==1? stock:stock<=0? 0:1;
    }

    @Override
    public List<Book> queryBookByIds(List<Long> ids) {
        List<Book> books = bookMapper.selectByIdList(ids);

        if(CollectionUtils.isEmpty(books)){
            throw new RaoraoBookShopException(ExceptionEnum.BOOK_NOT_FOUND);
        }
        for (Book book : books) {
            book.setInventory(bookMapper.selectBookStockById(book.getId()));
        }
        return books;
    }

    @Override
    @Transactional
    public void reduceStock(List<OrderData>list) {
        for (OrderData orderData : list) {
            int pos = bookMapper.reduceStock(orderData.getId(), orderData.getNum());
            if(pos!=1){
                throw new RaoraoBookShopException(ExceptionEnum.STOCK_ERROR);
            }
        }
    }
}
