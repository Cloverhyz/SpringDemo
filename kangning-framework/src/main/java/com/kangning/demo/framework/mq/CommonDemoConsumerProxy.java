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
 * @author 加康宁 Date: 2019-04-22 Time: 17:17
 * @version $Id$
 */
public class CommonDemoConsumerProxy {

    private static final Logger logger = LoggerFactory.getLogger(CommonDemoConsumerProxy.class);

    private final Map<String, BaseCommonConsumer> topics;

    private String nameSrv;

    private String groupName;

    private DefaultMQPushConsumer defaultMQPushConsumer;

    private String messageModel;

    public CommonDemoConsumerProxy(Map<String, BaseCommonConsumer> topics, String nameSrv, String groupName, String messageModel) {

        if (CollectionUtils.isEmpty(topics)){
            logger.error("no topic={} for group", groupName);
            topics = new HashMap<>(0);
        }
        this.topics = topics;
        this.nameSrv = nameSrv;
        this.groupName = groupName;
        this.defaultMQPushConsumer = new DefaultMQPushConsumer();
        this.messageModel = messageModel;

        MessageModel enumMessageModel = null;
        if (StringUtils.isNoneBlank(this.messageModel)){
            enumMessageModel = EnumUtils.getEnum(MessageModel.class, this.messageModel);
        }
        initConsumer(topics, nameSrv, groupName, enumMessageModel);
    }

    private void initConsumer(Map<String, BaseCommonConsumer> topics,String nameSrv, String groupName, MessageModel messageModel) {
        try {
            this.defaultMQPushConsumer.setNamesrvAddr(nameSrv);
            this.defaultMQPushConsumer.setConsumerGroup(groupName);
            this.defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            if (messageModel == null){
                this.defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
            }else {
                this.defaultMQPushConsumer.setMessageModel(messageModel);
            }
            for (Entry<String, BaseCommonConsumer> entry : topics.entrySet()){
                this.defaultMQPushConsumer.subscribe(entry.getKey(), "*");
            }
            this.defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt messageExt : msgs){
                        BaseCommonConsumer baseCommonConsumer = topics.get(messageExt.getTopic());
                        if (baseCommonConsumer == null){
                            logger.error("no topic={} for service", messageExt.getTopic());
                        }
                        try {
                            if (baseCommonConsumer== null || !baseCommonConsumer.receive(messageExt)){
                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            }
                        } catch (Exception var6) {
                            logger.error("consumer message error", var6);
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
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

    @Override
    public String toString() {
        return "CommonDemoConsumerProxy{" +
            "topics=" + topics +
            ", nameSrv='" + nameSrv + '\'' +
            ", groupName='" + groupName + '\'' +
            ", defaultMQPushConsumer=" + defaultMQPushConsumer +
            ", messageModel='" + messageModel + '\'' +
            '}';
    }
}
