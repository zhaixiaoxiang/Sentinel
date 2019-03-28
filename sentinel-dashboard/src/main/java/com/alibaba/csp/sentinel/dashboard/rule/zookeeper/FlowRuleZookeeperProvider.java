package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaixiaoxiang
 * @date   2019/3/26
 */
@Component("flowRuleZookeeperProvider")
public class FlowRuleZookeeperProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {
    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        CuratorFramework zkClient = ZookeeperClient.getZkClient();
        String path = String.format(ZookeeperConstant.FLOW_RULE_PATH, appName);
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            return null;
        }
        byte[] data = zkClient.getData().forPath(path);
        if (data != null) {
            return JSON.parseObject(new String(data), new TypeReference<List<FlowRuleEntity>>() {});
        }
        return null;
    }
}
