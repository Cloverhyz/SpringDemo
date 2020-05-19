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
 * @author kangning Date: 2019-04-22 Time: 15:50
 * @version $Id$
 */
public class CommonDemoProducerProxy {

    private static final Logger logger = LoggerFactory.getLogger(CommonDemoProducerProxy.class);

    private DefaultMQProducer defaultMQProducer;

    private String nameSrv;

    private String groupName;

    private String instanceName;

    private int retryTimes;

    public CommonDemoProducerProxy(String nameSrv, String groupName, String instanceName, int retryTimes) {
        try {
            this.nameSrv = nameSrv;
            this.groupName = groupName;
            this.instanceName = instanceName;
            this.retryTimes = retryTimes;
            //新建生产者对象
            this.defaultMQProducer = new DefaultMQProducer(groupName);
            //设置nameserver
            this.defaultMQProducer.setNamesrvAddr(nameSrv);
            //设置实例名称
            this.defaultMQProducer.setInstanceName(instanceName);
            //设置失败重试次数
            this.defaultMQProducer.setRetryTimesWhenSendFailed(retryTimes);
            //启动实例
            this.defaultMQProducer.start();
        } catch (MQClientException e) {
            logger.error("init rocket produce error", e);
        }
    }

    /**
     * 实际调用生产者发送消息的接口
     * @param message
     * @return
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
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

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    @Override
    public String toString() {
        return "CommonDemoProducerProxy{" +
            "defaultMQProducer=" + defaultMQProducer +
            ", nameSrv='" + nameSrv + '\'' +
            ", groupName='" + groupName + '\'' +
            '}';
    }
}
