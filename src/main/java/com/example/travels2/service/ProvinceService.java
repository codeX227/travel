package com.example.travels2.service;

import com.example.travels2.entity.Province;

import java.util.List;

public interface ProvinceService {

    /**
     * @param page 当前页
     * @param rows 每页显示记录数
     */
    List<Province> findByPage(Integer page,Integer rows);

    /**
     * 查询总条数
     */
    Integer findTotals();

    /**
     * 保存省份
     */
    void save(Province province);

    /**
     * 删除省份信息
     */
    void delete(String id);

    /**
     * 查询省份信息
     */
    Province findOne(String id);

    /**
     * 更新省份信息
     */
    void update(Province province);
}
