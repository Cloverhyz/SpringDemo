package com.kangning.demo.rocketmq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.kangning.demo.framework.mq.BaseCommonConsumer;
import com.kangning.demo.model.vo.RegionInfo;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author kangning Date: 2019-04-22 Time: 17:09
 * @version $Id$
 */
public class TestConsumer extends BaseCommonConsumer<RegionInfo> {

    @Override
    public boolean receive(MessageExt messageExt) {
        String msgStr = new String(messageExt.getBody());
        RegionInfo regionInfo = JSONObject.parseObject(msgStr,RegionInfo.class);
        System.out.println(regionInfo.getRegionCN());
        return true;
    }
}
