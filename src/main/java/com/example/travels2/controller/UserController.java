package com.example.travels2.controller;

import com.example.travels2.entity.Result;
import com.example.travels2.entity.User;
import com.example.travels2.service.UserService;
import com.example.travels2.utils.CreateImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin//跨域
@Slf4j
public class  UserController {

    @Autowired
    private UserService userService;

    /**
     * 验证码
     */
    @GetMapping("/getImage")
    public Map<String,String> getImage(HttpServletRequest request) throws IOException {

        HashMap<String, String> result = new HashMap<>();

        CreateImageCode createImageCode = new CreateImageCode();
        //获取验证码
        String securityCode = createImageCode.getCode();
        //验证码存入 session
        String key = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        request.getServletContext().setAttribute(key,securityCode);
        //生成图片
        BufferedImage image = createImageCode.getBuffImg();
        //base64编码
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image,"png",bos);
        String string = Base64Utils.encodeToString(bos.toByteArray());
        result.put("key",key);
        result.put("image",string);

        return result;
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(String code, String key, @RequestBody User user, HttpServletRequest request){

        Result result = new Result();

        log.info("接受的验证码"+code);
        log.info("接收到的key"+key);
        log.info("接受的user对象"+user);
        //验证验证码
        String keyCode = (String) request.getServletContext().getAttribute(key);
        log.info(keyCode);
        try {
            if (code.equalsIgnoreCase(keyCode)){
                //注册用户
                userService.register(user);
                result.setMsg("注册成功");
            } else {
                throw new RuntimeException("验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(e.getMessage()).setState(false);
        }
        return result;
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public Result login(@RequestBody User user,HttpServletRequest request){
        Result result = new Result();
        log.info("user : "+ user);

        try {
            User userDB = userService.login(user);
            //登录成功后保存用户标记
            request.getServletContext().setAttribute(userDB.getId(),userDB);
            result.setMsg("登陆成功").setUserId(userDB.getId());
        } catch (Exception e){
            result.setState(false).setMsg(e.getMessage());
        }

        return result;
    }
}
