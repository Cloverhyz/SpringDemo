package com.kangning.demo.framework.mq;

import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 加康宁 Date: 2019-04-22 Time: 16:39
 * @version $Id$
 */
public abstract class BaseCommonConsumer<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseCommonConsumer.class);

    public abstract boolean receive(MessageExt messageExt);
}
