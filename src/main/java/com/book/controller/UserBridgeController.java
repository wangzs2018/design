package com.book.controller;

import com.book.pojo.UserInfo;
import com.book.service.UserBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/bridge")
public class UserBridgeController {

    @Autowired
    private UserBridgeService userBridgeService;

    @PostMapping("/login")
    public String login(String account, String password) {
        return userBridgeService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return userBridgeService.register(userInfo);
    }

    @GetMapping("/gitee")
    public String gitee(HttpServletRequest request) throws IOException {
        return userBridgeService.login3rd(request, "GITEE");
    }
}
