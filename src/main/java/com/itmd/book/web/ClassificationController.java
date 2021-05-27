package com.itmd.book.web;

import com.itmd.book.service.ClassificationService;
import com.itmd.pojo.BookClassification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书分类接口
 */
@RestController
@RequestMapping("classification")
public class ClassificationController {
    @Autowired
    private ClassificationService classificationService;

    /**
     * 查询父id下面的所有子分类
     * @param preId
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<BookClassification>> queryClassification(@RequestParam("preId") Long preId) {
        return ResponseEntity.ok(classificationService.queryClassificationList(preId));
    }

    /**
     * 查询出版社对应的直接分类
     * @param pid
     * @return
     */
    @GetMapping("pid/list")
    public ResponseEntity<List<BookClassification>> queryBookPressClassificationByPid(@RequestParam("pid") Long pid){
        return ResponseEntity.ok(classificationService.queryBookPressClassificationByPid(pid));
    }

    /**
     * 根据ID集合查询分类集合信息
     * @return
     */
    @GetMapping("id/list")
    public ResponseEntity<List<BookClassification>> queryClassificationsByIdList(@RequestParam("idList") List<Long> idList){
        return ResponseEntity.ok(classificationService.queryClassificationByIdList(idList));
    }

    /**
     * 查询图书对应的直接分类
     * @param bid
     * @return
     */
    @GetMapping("bid/list")
    public ResponseEntity<List<BookClassification>> queryClassificationsByBid(@RequestParam("bid") Long bid){
        return ResponseEntity.ok(classificationService.queryClassificationByBid(bid));
    }
    @PostMapping("one")
    public ResponseEntity<Void> addClassificationOne(@RequestBody BookClassification bookClassification){
        classificationService.addClassificationOne(bookClassification);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @DeleteMapping("id")
    public ResponseEntity<Void> deleteClassification(@RequestParam(name = "id") Long id){
        classificationService.deleteClassification(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("id")
    public ResponseEntity<Void> updateClassification(@RequestBody BookClassification bookClassification){
        classificationService.updateClassification(bookClassification);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
