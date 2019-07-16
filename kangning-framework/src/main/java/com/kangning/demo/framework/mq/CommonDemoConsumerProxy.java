package com.kangning.demo.framework.mq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * @author kangning Date: 2019-04-22 Time: 17:17
 * @version $Id$
 */
public class CommonDemoConsumerProxy {

    private static final Logger logger = LoggerFactory.getLogger(CommonDemoConsumerProxy.class);

    private final Map<String, BaseCommonConsumer> topics;

    private String nameSrv;

    private String groupName;

    private DefaultMQPushConsumer defaultMQPushConsumer;

    private String messageModel;

    private int reconsumeTimes;

    public CommonDemoConsumerProxy(Map<String, BaseCommonConsumer> topics, String nameSrv, String groupName, String messageModel, int reconsumeTimes) {

        //检查消息队列配置
        if (CollectionUtils.isEmpty(topics)){
            logger.error("no topic={} for group", groupName);
            topics = new HashMap<>(0);
        }
        this.topics = topics;
        this.nameSrv = nameSrv;
        this.groupName = groupName;
        this.defaultMQPushConsumer = new DefaultMQPushConsumer();
        this.messageModel = messageModel;
        this.reconsumeTimes = reconsumeTimes;

        MessageModel enumMessageModel = null;
        if (StringUtils.isNoneBlank(this.messageModel)){
            enumMessageModel = EnumUtils.getEnum(MessageModel.class, this.messageModel);
        }
        //初始化消费者信息
        initConsumer(topics, nameSrv, groupName, enumMessageModel, reconsumeTimes);
    }

    private void initConsumer(Map<String, BaseCommonConsumer> topics,String nameSrv, String groupName, MessageModel messageModel, int reconsumeTimes) {
        try {
            //设置nameserver
            this.defaultMQPushConsumer.setNamesrvAddr(nameSrv);
            //设置消费者组
            this.defaultMQPushConsumer.setConsumerGroup(groupName);
            //设置启动时消费起始点
            this.defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //设置消费失败重试次数
            this.defaultMQPushConsumer.setMaxReconsumeTimes(reconsumeTimes);
            //设置消费模式
            if (messageModel == null){
                //默认集群模式
                this.defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
            }else {
                this.defaultMQPushConsumer.setMessageModel(messageModel);
            }
            //订阅消费队列
            for (Entry<String, BaseCommonConsumer> entry : topics.entrySet()){
                this.defaultMQPushConsumer.subscribe(entry.getKey(), "*");
            }
            //注册回调接口
            this.defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                //消费消息
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt messageExt : msgs){
                        //获取消息队列对应的具体消费者
                        BaseCommonConsumer baseCommonConsumer = topics.get(messageExt.getTopic());
                        if (baseCommonConsumer == null){
                            logger.error("no topic={} for service", messageExt.getTopic());
                        }
                        try {
                            //调用回调接口receive处理消息
                            if (baseCommonConsumer== null || !baseCommonConsumer.receive(messageExt)){
                                //返回消费失败
                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            }
                        } catch (Exception var6) {
                            logger.error("consumer message error", var6);
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                    //返回消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            //启动消费者实例
            this.defaultMQPushConsumer.start();
        } catch (MQClientException e) {
            logger.error("init rocket consumer error", e);
        }
    }

    public Map<String, BaseCommonConsumer> getTopics() {
        return topics;
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

    public DefaultMQPushConsumer getDefaultMQPushConsumer() {
        return defaultMQPushConsumer;
    }

    public void setDefaultMQPushConsumer(DefaultMQPushConsumer defaultMQPushConsumer) {
        this.defaultMQPushConsumer = defaultMQPushConsumer;
    }

    public String getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(String messageModel) {
        this.messageModel = messageModel;
    }

    public int getReconsumeTimes() {
        return reconsumeTimes;
    }

    public void setReconsumeTimes(int reconsumeTimes) {
        this.reconsumeTimes = reconsumeTimes;
    }

    @Override
    public String toString() {
        return "CommonDemoConsumerProxy{" +
            "topics=" + topics +
            ", nameSrv='" + nameSrv + '\'' +
            ", groupName='" + groupName + '\'' +
            ", defaultMQPushConsumer=" + defaultMQPushConsumer +
            ", messageModel='" + messageModel + '\'' +
            ", reconsumeTimes=" + reconsumeTimes +
            '}';
    }
}
