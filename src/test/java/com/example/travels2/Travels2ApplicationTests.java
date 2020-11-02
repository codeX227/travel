package com.example.travels2;

import com.example.travels2.entity.User;
import com.example.travels2.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Travels2ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("1111");
        user.setPassword("2222");
        user.setEmail("123@qq.com");
        userService.register(user);
    }


}
