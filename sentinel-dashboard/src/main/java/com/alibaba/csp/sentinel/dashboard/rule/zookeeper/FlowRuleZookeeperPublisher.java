package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperConstant;
import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaixiaoxiang
 * @date   2019/3/26
 */
@Component("flowRuleZookeeperPublisher")
public class FlowRuleZookeeperPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {
    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        CuratorFramework zkClient = ZookeeperClient.getZkClient();

        String path = String.format(ZookeeperConstant.FLOW_RULE_PATH, app);

        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        zkClient.setData().forPath(path, JSON.toJSONString(rules).getBytes());
    }
}
