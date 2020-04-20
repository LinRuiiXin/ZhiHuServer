package com.sz.ZhiHu.dao;

import com.sz.ZhiHu.po.Classify;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassifyDao {
    List<Classify> queryClassifyAboutKeyword(@Param("keyword") String keyword, @Param("start") int start,@Param("end") int end);
}
