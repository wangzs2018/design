package com.book.bridge.function;

import com.alibaba.fastjson.JSONObject;
import com.book.bridge.abst.factory.RegisterLoginComponentFactory;
import com.book.pojo.UserInfo;
import com.book.repo.UserRepository;
import com.book.utils.Constants;
import com.book.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 支持Gitee第三方账号登录功能
 */
@Component
public class RegisterLoginByGitee extends AbstractRegisterLoginFunc{
    @Value("${gitee.state}")
    private String giteeState;
    @Value("${gitee.token.url}")
    private String giteeTokenUrl;
    @Value("${gitee.user.url}")
    private String giteeUserUrl;
    @Value("${gitee.user.prefix}")
    private String giteeUserPrefix;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initFuncMap() {
        RegisterLoginComponentFactory.funcMap.put("GITEE", this);
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if(!giteeState.equals(state)) {
            throw new UnsupportedOperationException("Invalid state!");
        }

        //请求Gitee平台获取token，并携带code
        String tokenUrl = giteeTokenUrl.concat(code);
        JSONObject tokenResponse = HttpClientUtils.execute(tokenUrl, HttpMethod.POST);
        String token = String.valueOf(tokenResponse.get("access_token"));
        System.out.println(token);
        //请求用户信息，并携带 token
        String userUrl = giteeUserUrl.concat(token);
        JSONObject userInfoResponse = HttpClientUtils.execute(userUrl, HttpMethod.GET);

        //获取用户信息，userName添加前缀 GITEE@, 密码保持与userName一致。讨论过程请参见2.3小节
        String userName = giteeUserPrefix.concat(String.valueOf(userInfoResponse.get("name")));
        String password = userName;
        return autoRegister3rdAndLogin(userName, password);
    }

    private String autoRegister3rdAndLogin(String userName, String password) {
        //如果第三方账号已经登录过，则直接登录
        if(super.commonCheckUserExists(userName, userRepository)) {
            return super.commonLogin(userName, password, userRepository);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserPassword(password);
        userInfo.setCreateDate(new Date());

        //如果第三方账号是第一次登录，先进行“自动注册”
        super.commonRegister(userInfo, userRepository);
        //自动注册完成后，进行登录
        return super.commonLogin(userName, password, userRepository);
    }
}
