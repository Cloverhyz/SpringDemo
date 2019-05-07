package com.kangning.demo.framework.mq;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 加康宁 Date: 2019-04-22 Time: 15:50
 * @version $Id$
 */
public class CommonDemoProderProxy {

    private static final Logger logger = LoggerFactory.getLogger(CommonDemoProderProxy.class);

    private DefaultMQProducer defaultMQProducer;

    private String nameSrv;

    private String groupName;

    public CommonDemoProderProxy(String nameSrv, String groupName) {
        try {
            this.nameSrv = nameSrv;
            this.groupName = groupName;
            this.defaultMQProducer = new DefaultMQProducer(groupName);
            this.defaultMQProducer.setNamesrvAddr(nameSrv);
            this.defaultMQProducer.start();
        } catch (MQClientException e) {
            logger.error("init rocket produce error", e);
        }
    }

    public SendResult sendMsg(Message message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return defaultMQProducer.send(message);
    }


    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }

    public void setDefaultMQProducer(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }

    public String getNameSrv() {
        return nameSrv;
    }

    public void setNameSrv(String nameSrv) {
        this.nameSrv = nameSrv;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "CommonDemoProderProxy{" +
            "defaultMQProducer=" + defaultMQProducer +
            ", nameSrv='" + nameSrv + '\'' +
            ", groupName='" + groupName + '\'' +
            '}';
    }
}
