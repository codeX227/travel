package com.example.travels2.controller;

import com.example.travels2.entity.Province;
import com.example.travels2.entity.Result;
import com.example.travels2.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    /**
     * 查询所有
     */
    @GetMapping("/findByPage")
    public Map<String,Object> findByPage(Integer page, Integer rows){
        page = page == null ? 1 : page;
        rows = rows == null ? 4 : rows;

        HashMap<String, Object> map = new HashMap<>();
        //分页处理
        List<Province> provinces = provinceService.findByPage(page, rows);
        //计算总页数
        Integer totals = provinceService.findTotals();
        Integer totalPage = totals % rows == 0 ? totals/rows : totals/rows+1;

        map.put("provinces",provinces);
        map.put("totals",totals);
        map.put("totalPage",totalPage);
        map.put("page",page);

        return map;
    }

    /**
     * 保存省份信息
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Province province){
        Result result = new Result();
        try {
            provinceService.save(province);
            result.setMsg("保存省份信息成功");
        } catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg("保存省份信息失败");
        }
        return result;
    }

    /**
     * 保存省份信息
     */
    @RequestMapping("/delete")
    public Result delete(String id){
        Result result = new Result();
        try {
            provinceService.delete(id);
            result.setMsg("删除省份信息成功");
        } catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg("删除省份信息失败");
        }
        return result;
    }

    /**
     * 查询一个省份信息
     */
    @RequestMapping("/findOne")
    public Province findOne(String id){
        return provinceService.findOne(id);
    }

    /**
     * 修改省份信息的方法
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Province province){
        Result result = new Result();
        try {
            provinceService.update(province);
            result.setMsg("修改信息成功");
        } catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg("修改信息失败");
        }
        return result;
    }
}
