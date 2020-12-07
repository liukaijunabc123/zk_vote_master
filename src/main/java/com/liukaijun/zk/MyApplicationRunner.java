package com.liukaijun.zk;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author liukj
 * @date 2020/12/7 17:00
 * @package com.liukaijun.zk
 * @description
 */
@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

    /**
     * 创建zk连接
      */
    ZkClient zkClient = new ZkClient("192.168.1.68:2181");
    private String path = "/election";
    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("项目启动完成...");
        createEphemeral();
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                // 主节点已经挂了，重新选举
                log.info("主节点已经挂掉了，请重新开始选举.....");
                createEphemeral();
            }
        });
    }


    private void createEphemeral() {
        try {
            zkClient.createEphemeral(path, serverPort);
            ElectionMaster.isSurvival = true;
            log.info("serverPort：" + serverPort + ",选举成功....");
        } catch (Exception e) {
            ElectionMaster.isSurvival = false;
        }
    }
}
