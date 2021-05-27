package com.itmd.book.mapper;

import com.itmd.pojo.BookClassification;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ClassificationMapper extends Mapper<BookClassification>, IdListMapper<BookClassification,Long> {

    @Select("SELECT * FROM tb_classification as c INNER JOIN tb_press_classification as pc ON c.id = pc.cid WHERE pc.pid = #{pid}")
    List<BookClassification> queryClassificationByPid(@Param("pid") Long pid);

    @Select("SELECT * FROM tb_classification as c INNER JOIN tb_book_classification as bc ON c.id = bc.pre_id WHERE bc.b_id = #{bid}")
    List<BookClassification> queryClassificationByBid(@Param("bid") Long bid);
}
