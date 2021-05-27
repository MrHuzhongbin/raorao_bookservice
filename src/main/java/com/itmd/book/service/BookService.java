package com.itmd.book.service;

import com.itmd.book.vi.OrderData;
import com.itmd.pojo.Book;
import com.itmd.vo.BookLw;
import com.itmd.vo.PageResult;

import java.util.List;

public interface BookService {


    public void addBookInfo(Book book);

    /**
     * 分页查询部分图书信息
     * @param page
     * @param rows
     * @param key
     * @param saleable
     * @return
     */
    public PageResult<BookLw> queryBookInfoByPage(Integer page, Integer rows, String key,Boolean saleable);

    /**
     * 修改图书信息
     * @param book
     */
    public void updateBookInfoById(Book book);

    /**
     * 修改图书的上架状态
     * @param id
     */
    public void updateBookInfoById(Long id,Boolean saleable);

    public void addBookClassification(Book book);

    public BookLw queryBookById(Long id);

    public Integer queryBookStockById(Long id,int type);

    List<Book> queryBookByIds(List<Long> ids);

    void reduceStock(List<OrderData> list);
}
