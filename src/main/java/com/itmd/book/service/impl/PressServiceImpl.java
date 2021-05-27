package com.itmd.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itmd.book.mapper.ClassificationMapper;
import com.itmd.book.mapper.PressMapper;
import com.itmd.book.service.PressService;
import com.itmd.enums.ExceptionEnum;
import com.itmd.exception.RaoraoBookShopException;
import com.itmd.pojo.BookClassification;
import com.itmd.pojo.BookPress;
import com.itmd.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PressServiceImpl implements PressService {
    @Autowired
    private PressMapper pressMapper;

    @Override
    public PageResult<BookPress> queryBookPressByPage(Integer page, Integer rows, String key, String sortBy, Boolean desc) {
        //分页
        PageHelper.startPage(page,rows);
        /**
         * WHERE 'NAME' LIKE "%X%" OR LETTER =='X' ORDER BY ID DESC
         */
        //过滤
        Example example = new Example(BookPress.class);
        if(StringUtils.isNotBlank(key)){
            //过滤条件
            example.createCriteria().orLike("name", "%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy +" "+(desc ? "DESC":"ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<BookPress> list = pressMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new RaoraoBookShopException(ExceptionEnum.PRESS_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<BookPress> info = new PageInfo<BookPress>(list);
        return new PageResult<BookPress>(info.getTotal(),list);
    }

    @Override
    @Transactional
    public void addBookPress(BookPress bookPress) {
        int status = pressMapper.insert(bookPress);
        if(status != 1){
            throw new RaoraoBookShopException(ExceptionEnum.PRESS_SAVE_ERROR);

        }
    }

    @Override
    @Transactional
    public void updateBookPress(BookPress bookPress) {
        int status = pressMapper.updateByPrimaryKey(bookPress);
        if(status != 1){
            throw new RaoraoBookShopException(ExceptionEnum.PRESS_UPDATE_ERROR);
        }
    }

    @Override
    @Transactional
    public void deletePressClassificationByCidAndPid(Long pid,Long cid) {
        int status = pressMapper.deleteClassificationPressByCidAndPid(pid,cid);
        if(status != 1){
            throw new RaoraoBookShopException(ExceptionEnum.PRESS_DELETE_ERROR);
        }
    }

    @Override
    @Transactional
    public void addBookPressClassification(Long pid, Long cid) {
        int status = pressMapper.insertClassificationPress(cid, pid);
        if(status != 1 ){
            throw new RaoraoBookShopException(ExceptionEnum.PRESS_SAVE_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteBookPressByPid(Long pid) {
        int status = pressMapper.deleteByPrimaryKey(pid);
        if(status != 1 ){
            throw new RaoraoBookShopException(ExceptionEnum.PRESS_DELETE_ERROR);
        }
    }

    @Override
    public List<BookPress> queryPressByIds(List<Long> idList) {
        List<BookPress> bookPresses = pressMapper.selectByIdList(idList);
        if(CollectionUtils.isEmpty(bookPresses)){
            throw new RaoraoBookShopException(ExceptionEnum.PRESS_NOT_FOUND);
        }
        return bookPresses;
    }

}
