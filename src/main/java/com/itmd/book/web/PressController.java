package com.itmd.book.web;

import com.itmd.book.service.PressService;
import com.itmd.pojo.BookPress;
import com.itmd.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书出版社接口
 */
@RestController
@RequestMapping("press")
public class PressController {

    @Autowired
    private PressService pressService;

    @GetMapping("page")
    public ResponseEntity<PageResult<BookPress>> queryBookPressByPage(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "rows",defaultValue = "5") Integer rows,
            @RequestParam(name = "key",required = false) String key,
            @RequestParam(name = "sortBy",required = false) String sortBy,
            @RequestParam(name = "desc",defaultValue = "false") Boolean desc
    ){
        return ResponseEntity.ok(pressService.queryBookPressByPage(page,rows,key,sortBy,desc));
    }

    /**
     * ID集合批量查询出版社
     * @param idList
     * @return
     */
    @GetMapping("id/list")
    public ResponseEntity<List<BookPress>> queryPressByIds(@RequestParam("idList")List<Long>idList){
        return ResponseEntity.ok(pressService.queryPressByIds(idList));
    }
    @PostMapping("one")
    public ResponseEntity<Void> addBookPress(@RequestBody  BookPress bookPress){
        pressService.addBookPress(bookPress);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("one/classification")
    public ResponseEntity<Void> addBookPressClassification(
            @RequestParam("pid")Long pid,
            @RequestParam("cid")Long cid
    ){
        pressService.addBookPressClassification(pid,cid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("id")
    public ResponseEntity<Void> updateBookPress(@RequestBody BookPress bookPress){
        pressService.updateBookPress(bookPress);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("id/classification")
    public ResponseEntity<Void> deletePressClassification(@RequestParam("pid")Long pid,@RequestParam("cid")Long cid){
        pressService.deletePressClassificationByCidAndPid(pid,cid);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
    @DeleteMapping("id")
    public ResponseEntity<Void> deleteBookPress(@RequestParam("pid")Long pid){
        pressService.deleteBookPressByPid(pid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
