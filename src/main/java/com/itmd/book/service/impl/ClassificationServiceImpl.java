package com.itmd.book.service.impl;


import com.itmd.book.mapper.ClassificationMapper;
import com.itmd.book.service.ClassificationService;
import com.itmd.enums.ExceptionEnum;
import com.itmd.exception.RaoraoBookShopException;
import com.itmd.pojo.BookClassification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class ClassificationServiceImpl implements ClassificationService {
    @Autowired
    private ClassificationMapper classificationMapper;

    @Override
    public List<BookClassification> queryClassificationList(Long preId) {
        BookClassification bookClassification = new BookClassification();
        bookClassification.setPreId(preId);
        List<BookClassification> bookClassifications = classificationMapper.select(bookClassification);
        if(CollectionUtils.isEmpty(bookClassifications)){
            throw new RaoraoBookShopException(ExceptionEnum.CLASSIFICATION_NOT_FOUND);
        }
        return bookClassifications;
    }

    @Override
    public List<BookClassification> queryBookPressClassificationByPid(Long pid) {
        List<BookClassification> bookClassifications = classificationMapper.queryClassificationByPid(pid);
        if(CollectionUtils.isEmpty(bookClassifications)){
            throw new RaoraoBookShopException(ExceptionEnum.CLASSIFICATION_NOT_FOUND);
        }
        return bookClassifications;
    }

    @Override
    public List<BookClassification> queryClassificationByBid(Long bid) {
        List<BookClassification> bookClassifications = classificationMapper.queryClassificationByBid(bid);
        if(CollectionUtils.isEmpty(bookClassifications)){
            throw  new RaoraoBookShopException(ExceptionEnum.CLASSIFICATION_NOT_FOUND);
        }
        return bookClassifications;
    }

    @Override
    public List<BookClassification> queryClassificationByIdList(List<Long> idList) {
        List<BookClassification> bookClassifications = classificationMapper.selectByIdList(idList);
        if(CollectionUtils.isEmpty(bookClassifications)){
            throw  new RaoraoBookShopException(ExceptionEnum.CLASSIFICATION_NOT_FOUND);
        }
        return bookClassifications;
    }

    @Override
    @Transactional
    public void addClassificationOne(BookClassification bookClassification) {
        log.info(bookClassification.toString());
        int status = classificationMapper.insert(bookClassification);
        if(status != 1){
            throw new RaoraoBookShopException(ExceptionEnum.CLASSIFICATION_SAVE_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteClassification(Long id) {
        int status = classificationMapper.deleteByPrimaryKey(id);
        if(status != 1){
            throw new RaoraoBookShopException(ExceptionEnum.CLASSIFICATION_DELETE_ERROR);
        }
    }

    @Override
    public void updateClassification(BookClassification bookClassification) {
        int status = classificationMapper.updateByPrimaryKey(bookClassification);
        if(status != 1){
            throw new RaoraoBookShopException(ExceptionEnum.CLASSIFICATION_UPDATE_ERROR);
        }
    }
}
