package com.book.service;

import com.book.dutychain.AbstractBusinessHandler;
import com.book.dutychain.CityHandler;
import com.book.dutychain.LaunchTarget;
import com.book.dutychain.builder.BusinessChainBuilder;
import com.book.dutychain.builder.HandlerEnum;
import com.book.pojo.BusinessLaunch;
import com.book.pojo.UserInfo;
import com.book.repo.BusinessLaunchRepository;
import com.book.repo.UserRepository;
import com.book.ticket.proxy.DirectorProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private DirectorProxy directorProxy;

    @Autowired
    private UserRepository userRepository;

    //注入BusinessLaunchRepository，用于查询业务投放数据
    @Autowired
    private BusinessLaunchRepository businessLaunchRepository;
    //注入duty.chain
    @Value("${duty.chain}")
    private String handlerType;
    //记录当前handlerType的配置，判断duty.chain的配置是否有修改
    private String currentHandlerType;
    //记录当前的责任链头节点，如果配置没有修改，下次直接返回即可
    private AbstractBusinessHandler currentHandler;

    public String login(String account, String password) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        if(userInfo == null) {
            return "account / password ERROR!";
        }
        return "Login Success";
    }

    public String register(UserInfo userInfo) {
        if(checkUserExists(userInfo.getUserName())) {
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());
        userRepository.save(userInfo);
        return "Register Success!";
    }

    public boolean checkUserExists(String userName) {
        UserInfo user = userRepository.findByUserName(userName);
        if(user == null) {
            return false;
        }
        return true;
    }

    public List<BusinessLaunch> filterBusinessLaunch(LaunchTarget launchTarget) {
        List<BusinessLaunch> launchList = businessLaunchRepository.findAll();
        log.info("原始业务投放数据：{}", launchList);
        return buildChain().processHandler(launchList, launchTarget);
    }
    //组装责任链条并返回责任链条首节点
    private AbstractBusinessHandler buildChain() {
        //如果没有配置，直接返回null
        if(handlerType == null) {
            return null;
        }
        //如果是第一次配置，将handlerType记录下来
        if(currentHandlerType == null) {
            this.currentHandlerType = this.handlerType;
        }
        //说明duty.chain的配置并未修改 且 currentHandler不为null，直接返回currentHandler
        if(this.handlerType.equals(currentHandlerType) && this.currentHandler != null) {
            return this.currentHandler;
        } else { //说明duty.chain的配置有修改，需要从新初始化责任链条
            //从新初始化责任链条，需要保证线程安全，仅仅每次修改配置时才会执行一次此处的代码，无性能问题
            System.out.println("配置有修改或首次初始化，组装责任链条！！！");
            synchronized (this) {
                try {
                    //创建哑结点，随意找一个具体类型创建即可
                    AbstractBusinessHandler dummyHeadHandler = new CityHandler();
                    //创建前置节点，初始赋值为哑结点
                    AbstractBusinessHandler preHandler = dummyHeadHandler;
                    //将duty.chain的配置用 逗号 分割为 list类型，并通过HandlerEnum创建责任类，并配置责任链条
                    List<String> handlerTypeList = Arrays.asList(handlerType.split(","));
                    for(String handlerType : handlerTypeList) {
                        AbstractBusinessHandler handler =
                                (AbstractBusinessHandler) Class.forName(HandlerEnum.valueOf(handlerType).getValue()).newInstance();
                        preHandler.nextHandler = handler;
                        preHandler = handler;
                    }
                    //从新赋值新的责任链头节点
                    this.currentHandler = dummyHeadHandler.nextHandler;
                    //从新赋值修改后的配置
                    this.currentHandlerType = this.handlerType;
                    //返回责任链头节点
                    return currentHandler;
                } catch (Exception e) {
                    throw new UnsupportedOperationException(e);
                }
            }
        }
    }

    public Object createTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        return directorProxy.buildTicket(type, productId, content, title, bankInfo, taxId);
    }


}
