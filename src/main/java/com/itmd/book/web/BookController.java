package com.itmd.book.web;

import com.itmd.book.service.BookService;
import com.itmd.book.vi.OrderData;
import com.itmd.pojo.Book;
import com.itmd.vo.BookLw;
import com.itmd.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("primary")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("one")
    public ResponseEntity<Void> addBookInfo(@RequestBody Book book){
        System.out.println(book);
        bookService.addBookInfo(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("page")
    public ResponseEntity<PageResult<BookLw>>queryBookInfoByPage(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "rows",defaultValue = "5") Integer rows,
            @RequestParam(name = "key",required = false) String key,
            @RequestParam(name="saleable",required = false) Boolean saleable
    ){
        return ResponseEntity.ok(bookService.queryBookInfoByPage(page,rows,key,saleable));
    }
    @GetMapping("id")
    public ResponseEntity<BookLw>queryBookById(@RequestParam("id") Long id){
        return ResponseEntity.ok(bookService.queryBookById(id));
    }
    /**
     * 查询图书信息
     * @param ids
     * @return
     */
    @GetMapping("ids")
    public ResponseEntity<List<Book>> queryBookByIds(@RequestParam("ids")List<Long>ids){
        return ResponseEntity.ok(bookService.queryBookByIds(ids));
    }

    @PutMapping("id")
    public ResponseEntity<Void> updateBookInfoById(@RequestBody Book book){

        bookService.updateBookInfoById(book);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PutMapping("id/saleable")
    public ResponseEntity<Void> updateBookInfoById(@RequestParam("id") Long id,@RequestParam("saleable")Boolean saleable){
        bookService.updateBookInfoById(id,saleable);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 查询图书库存
     * @param id
     * @return
     */
    @GetMapping("stock/{id}/{type}")
    public ResponseEntity<Integer> queryBookStockById(@PathVariable("id") Long id,@PathVariable("type") int type){
        return ResponseEntity.ok(bookService.queryBookStockById(id,type));
    }

    /**
     * 减库存
     * @param list
     * @return
     */
    @PutMapping("stock/reduce")
    public ResponseEntity<Void> reduceStock(@RequestBody List<OrderData> list){
        bookService.reduceStock(list);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
