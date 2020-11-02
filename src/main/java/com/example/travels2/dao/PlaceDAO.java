package com.example.travels2.dao;

import com.example.travels2.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PlaceDAO extends BaseDAO<Place,String> {

    List<Place> findByProvinceIdPage(@Param("start") Integer start,@Param("rows") Integer rows,@Param("provinceId") String provinceId);

    Integer findByProvinceIdCounts(String provinceId);
}
