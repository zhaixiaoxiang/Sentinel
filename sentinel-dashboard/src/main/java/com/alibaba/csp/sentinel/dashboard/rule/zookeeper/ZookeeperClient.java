package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author zhaixiaoxiang
 * @date   2019/3/26
 */
public class ZookeeperClient {
    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;

    public static CuratorFramework getZkClient() {
        return zkClient;
    }

    private static CuratorFramework zkClient;

    static {
        zkClient = CuratorFrameworkFactory.newClient(System.getProperty("zkAddress", "localhost:2181"),
                new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
        zkClient.start();
    }
}
