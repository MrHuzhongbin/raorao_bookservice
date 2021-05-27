package com.itmd.book.service;

import com.itmd.pojo.BookClassification;
import com.itmd.pojo.BookPress;
import com.itmd.vo.PageResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PressService {
    /**
     * 分页查询出版社
     * @param page
     * @param rows
     * @param key
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<BookPress> queryBookPressByPage(Integer page, Integer rows, String key, String sortBy, Boolean desc);

    /**
     * 添加出版社信息
     * @param bookPress
     */
    public void addBookPress(BookPress bookPress);

    /**
     * 修改出版社信息
     * @param bookPress
     */
    public void updateBookPress(BookPress bookPress);

    /**
     * 删除出版社分类
     * @param pid
     * @param cid
     */
    public void deletePressClassificationByCidAndPid(Long pid,Long cid);

    /**
     * 添加出版社分类
     * @param pid
     * @param cid
     */
    public void addBookPressClassification(Long pid, Long cid);

    /**
     * 删除出版社
     * @param pid
     */
    public void deleteBookPressByPid(Long pid);

    List<BookPress> queryPressByIds(List<Long> idList);
}
