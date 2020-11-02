package com.example.travels2.controller;

import com.example.travels2.entity.Place;
import com.example.travels2.entity.Result;
import com.example.travels2.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/place")
@CrossOrigin
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Value("${upload.dir}")
    private String realPath ;

    /**
     * 根据省份id查询景点
     */
    @GetMapping("/findAllPlace")
    public Map<String, Object> findAllPlace(Integer page, Integer rows, String provinceId){
        HashMap<String, Object> result = new HashMap<>();
        page = page == null ? 1 : page;
        rows = rows == null ? 3 : rows;

        //景点集合
        List<Place> places = placeService.findByProvinceIdPage(page, rows, provinceId);
        //景点数
        Integer counts = placeService.findByProvinceIdCounts(provinceId);
        //总页数
        Integer totalPage = counts%rows == 0 ? counts/rows : counts/rows+1;

        result.put("places",places);
        result.put("page",page);
        result.put("counts",counts);
        result.put("totalPage",totalPage);

        return result;
    }

    /**
     * 保存景点信息
     */
    @PostMapping("/save")
    public Result save(MultipartFile pic,Place place) throws IOException {//前端formData发送数据，不用@RequestBody接收
        Result result = new Result();

        try {
            //文件上传
            //commons-fileupload org.apache.commons.io.FilenameUtils 获取文件扩展名
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
            //新文件名
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +'.'+ extension;
            //Base64编码处理
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));
            pic.transferTo(new File(realPath+newFilename));

            //保存 place 对象
            placeService.save(place);
            result.setMsg("保存景点信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }

        return result;
    }

    /**
     * 删除景点
     */
    @GetMapping("/delete")
    public Result delete(String id){
        Result result = new Result();
        try {
            placeService.delete(id);
            result.setMsg("删除景点成功");
        } catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }

        return result;
    }

    /**
     * 查询景点
     */
    @GetMapping("/findOne")
    public Place findOne(String id){
        return placeService.findOne(id);
    }

    /**
     * 修改景点
     */
    @PostMapping("/update")
    public Result update(MultipartFile pic,Place place) throws IOException {
        Result result = new Result();

        try {
            //对接收文件做Base64
            String picpath = Base64Utils.encodeToString(pic.getBytes());
            place.setPicpath(picpath);
            //处理文件上传
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ '.' + extension;
            pic.transferTo(new File(realPath,newFileName));

            //修改景点信息
            placeService.update(place);
            result.setMsg("修改景点信息成功");
        } catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }
}
