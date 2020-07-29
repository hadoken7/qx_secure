package com.happypeng.qx.secure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecureApplicationTests {

    @Autowired
    private SecureService secureService;

    @Test
    void contextLoads() {
//        String content = secureService.get(url);
//        System.out.println(content);
    }




}
