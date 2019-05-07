package com.kangning.demo.service;

import com.kangning.demo.model.vo.PCData;

import java.io.IOException;
import java.util.List;

/**
 * @author 加康宁 Date: 2018-08-24 Time: 22:17
 * @version $Id$
 */
public interface RbaHousePushService {

    /**
     * 根据房屋Id列表推送房屋数据
     * @param houseIds
     * @return
     */
    void pushHouseByIdList(List<Long> houseIds) throws IOException, InterruptedException;

    /**
     *  订阅房屋上下架状态-->推送
     * @param houseStatusList
     * @return
     */
    void pushHouseStatus(List<PCData> houseStatusList) throws IOException, InterruptedException;
}
