package com.itmd.book.mapper;

import com.itmd.pojo.BookClassification;
import com.itmd.pojo.BookPress;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;



public interface PressMapper extends Mapper<BookPress>, IdListMapper<BookPress,Long> {

    @Insert("INSERT INTO tb_press_classification (pid,cid) VALUES(#{pid},#{cid})")
    int insertClassificationPress(@Param("cid") Long cid, @Param("pid") Long pid);

    @Delete("DELETE FROM tb_press_classification WHERE pid = #{pid} and cid = #{cid}")
    int deleteClassificationPressByCidAndPid(@Param("pid") Long pid,@Param("cid")Long cid);

}
