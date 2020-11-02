package com.example.travels2.dao;

import com.example.travels2.entity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ProvinceDAO extends BaseDAO<Province,String> {


}
