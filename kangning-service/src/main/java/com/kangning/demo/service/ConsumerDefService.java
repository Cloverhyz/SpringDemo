package com.kangning.demo.service;

import com.kangning.demo.model.vo.PCData;

/**
 * @author 加康宁 Date: 2018-08-28 Time: 10:54
 * @version $Id$
 */
public interface ConsumerDefService {

    void pushData(PCData pcData);


    void pushByDelay(PCData pcdata) throws InterruptedException;
}
