package com.kangning.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.kangning.demo.model.vo.PCData;
import com.kangning.demo.service.RbaHousePushService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 加康宁 Date: 2018-08-24 Time: 22:17
 * @version $Id$
 */
@Service("rbaHousePushService")
public class RbaHousePushServiceImpl extends BasePushCService<PCData> implements RbaHousePushService {


    @Override
    public void pushHouseByIdList(List<Long> houseIds) throws IOException, InterruptedException {

        List<PCData> pcDataList = new ArrayList<>();

        //循环查询转换后的房屋详情进行推送
        for (Long houseId : houseIds) {

            PCData pcData = new PCData();
            pcData.set(houseId);
            if (pcData == null) {
                continue;
            }
            pcDataList.add(pcData);
        }
        if (pcDataList.size() > 0) {
            super.pushByDelay(pcDataList);
        }
//        super.pushDataList(pcDataList);
    }


    /**
     * @param houseStatusList
     * @return
     * @throws IOException
     * @function 推送房型上下架状态
     */
    @Override
    public void pushHouseStatus(List<PCData> houseStatusList) throws IOException, InterruptedException {

        String body = JSONArray.toJSONString(houseStatusList);

        Thread.sleep(1000 * 1);
        return;
    }

    @Override
    protected String getSyncUrl() {
        return "测试";
    }

    @Override
    protected void dealWithResponse(List<PCData> list) {

    }
}
