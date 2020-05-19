package com.kangning.demo.service.impl;

import com.kangning.demo.model.vo.RegionInfo;
import com.kangning.demo.rocketmq.producer.TestProducer;
import com.kangning.demo.service.DemoService;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-25 Time: 11:53
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Autowired
    private TestProducer testProducer;
    @Override
    public Boolean insertTest(RegionInfo regionInfo) {
        SendResult sendResult = testProducer.sendMsg(regionInfo);
        if (sendResult == null){
            return  false;
        }
        sendResult.getSendStatus();
        System.out.println(sendResult.getMsgId());
        return true;
    }
}
