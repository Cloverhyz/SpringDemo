package com.kangning.demo.framework.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kangning Date: 2019-04-22 Time: 16:39
 * @version $Id$
 */
public abstract class BaseCommonProducer<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseCommonProducer.class);

    @Autowired
    private CommonDemoProducerProxy commonDemoProducerProxy;

    private String topicName;

    public SendResult sendMsg(T object){
        Message message = new Message(this.getTopicName(), JSONObject.toJSONString(object).getBytes());
        try {
            return commonDemoProducerProxy.sendMsg(message);
        } catch (InterruptedException | RemotingException | MQClientException | MQBrokerException e) {
            logger.error("send rocketMq msg error", e);
            e.printStackTrace();
        }
        return null;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
