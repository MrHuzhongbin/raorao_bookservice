package com.itmd.book;

import com.itmd.book.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BookApplicationTests {

    @Autowired
    private BookMapper mapper;
    private  int num=0;

    @Test
    void contextLoads() {

    }
}
