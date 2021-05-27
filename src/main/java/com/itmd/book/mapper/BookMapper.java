package com.itmd.book.mapper;


import com.itmd.pojo.Book;
import com.itmd.pojo.BookClassification;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;


public interface BookMapper extends Mapper<Book>, IdListMapper<Book,Long> {

    @Insert("INSERT INTO tb_book_classification (pre_id,b_id) VALUES(#{preId},#{bid})")
    int insertBookClassificationMid(@Param("preId") Long preId, @Param("bid") Long bid);

    @Update("UPDATE tb_book SET saleable = #{saleable} WHERE id = #{id}")
    int updateBookSaleableById(@Param("id")Long id,@Param("saleable")Boolean saleable);

    @Insert("INSERT INTO tb_stock (b_id,stock) VALUES(#{bid},#{stock})")
    int insertBookStock(@Param("bid") Long bid, @Param("stock") Integer stock);

    @Update("UPDATE tb_stock SET stock = #{stock} WHERE b_id = #{id}")
    int updateBookStockById(@Param("id")Long id,@Param("stock")Integer stock);

    @Delete("DELETE FROM tb_book_classification WHERE b_id = #{id}")
    int deleteBookClassificationMid(@Param("id")Long id);

    @Select("SELECT stock FROM tb_stock WHERE b_id = #{id}")
    int selectBookStockById(@Param("id")Long id);

    @Update("UPDATE tb_stock SET stock = stock - #{num} WHERE b_id = #{id} AND stock >= #{num}")
    int reduceStock(@Param("id")Long id,@Param("num")int num);
}
