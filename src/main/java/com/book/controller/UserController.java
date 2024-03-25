package com.book.controller;

import com.book.adapter.Login3rdAdapter;
import com.book.dutychain.LaunchTarget;
import com.book.pojo.BusinessLaunch;
import com.book.pojo.UserInfo;
import com.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Login3rdAdapter login3rdAdapter;

    @PostMapping("/login")
    public String login(String account, String password) {
        return login3rdAdapter.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return login3rdAdapter.register(userInfo);
    }

    @GetMapping("/gitee")
    public String gitee(String code, String state) throws IOException {
        return login3rdAdapter.loginByGitee(code, state);
    }

    @PostMapping("/business/launch")
    public List<BusinessLaunch> filterBusinessLaunch(@RequestBody LaunchTarget launchTarget) {
        return userService.filterBusinessLaunch(launchTarget);
    }
    @PostMapping("/ticket")
    public Object createTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        return userService.createTicket(type, productId, content, title, bankInfo, taxId);
    }
}
