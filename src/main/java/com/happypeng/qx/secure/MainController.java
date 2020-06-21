package com.happypeng.qx.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hadoken
 * @date 2020/6/18
 */

@RestController
public class MainController {

    @Autowired
    private SecureService secureService;

    @GetMapping("url/{index}")
    public Result encryptedUrlContent(@PathVariable int index) {
        return secureService.encryptedUrlContent(index);
    }

    @GetMapping("plain/{index}")
    public Result encryptedPlainContent(@PathVariable int index) {
        return secureService.encryptedPlainContent(index);
    }

}
