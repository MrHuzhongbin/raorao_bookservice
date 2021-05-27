package com.itmd.book.service;

import com.itmd.pojo.BookClassification;
import java.util.List;

public interface ClassificationService {

    public List<BookClassification> queryClassificationList(Long preId);

    public void addClassificationOne(BookClassification bookClassification);

    public void deleteClassification(Long id);

    public void updateClassification(BookClassification bookClassification);

    public List<BookClassification> queryBookPressClassificationByPid(Long pid);

    public List<BookClassification> queryClassificationByBid(Long bid);

    List<BookClassification> queryClassificationByIdList(List<Long> idList);
}
