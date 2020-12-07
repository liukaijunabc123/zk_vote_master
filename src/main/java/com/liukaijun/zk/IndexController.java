package com.liukaijun.zk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liukj
 * @date 2020/12/7 16:55
 * @package com.liukaijun.zk
 * @description
 */
@Slf4j
@RestController
public class IndexController {

    @Value("${server.port}")
    private String serverPort;


    public static void main(String[] args) {
        /**
         * 1、项目启动的时候，会在zk上创建一个相同的临时节点。
         * 2、谁能够创建成功，谁就为主节点服务器。
         * 3、监听节点是否被删除，如果接收到节点被删除，重新开始选举（重新开始创建节点）
         */

    }

    // 获取服务信息
    @RequestMapping("/getServerInfo")
    public String getServerInfo() {
        return "serverPort"+serverPort+ (ElectionMaster.isSurvival ? "当前服务器为主节点" : "当前服务器为从节点");
    }
}
