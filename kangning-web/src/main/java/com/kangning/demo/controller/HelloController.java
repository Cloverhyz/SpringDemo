package com.kangning.demo.controller;

import com.kangning.demo.model.vo.PCData;
import com.kangning.demo.model.vo.PersonInfoVo;
import com.kangning.demo.model.vo.RegionInfo;
import com.kangning.demo.service.ConsumerDefService;
import com.kangning.demo.service.DemoService;
import com.kangning.demo.service.HousePushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-19 Time: 18:03
 */
@Controller
@RequestMapping("/demo")
public class HelloController {

    @Autowired
    private ConsumerDefService consumerDefService;
    @Autowired
    private HousePushService housePushService;
    @Autowired
    private DemoService demoService;

    @RequestMapping("test")
    @ResponseBody
    public String demoTest() throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            PCData pcData = new PCData();
            pcData.set(Long.valueOf(i));
//            consumerDefService.pushData(pcData);
        }

        for (int i = 10; i < 20; i++) {
            List<Long> pcDataList = new ArrayList<>();
            PCData pcData = new PCData();
            pcData.set(Long.valueOf(i));
            pcDataList.add(Long.valueOf(i + ""));
//            consumerDefService.pushByDelay(pcData);
            Thread.sleep(100 * 1);
            housePushService.pushHouseByIdList(pcDataList);
        }
        System.out.println("\n======生产完毕======\n");
        return "Hello ";
    }

    @RequestMapping("/sendmsg")
    @ResponseBody
    public Boolean testSendMsg(){
        RegionInfo regionInfo = new RegionInfo();
        regionInfo.setParentRegionId(414L);
        regionInfo.setRegionCN("三亚");
        regionInfo.setRegionEN("SY");
        regionInfo.setRegionId(1L);
        regionInfo.setRegionLevel(4);
        return demoService.insertTest(regionInfo);
    }


    @RequestMapping("/testmsg")
    @ResponseBody
    public Boolean testSendMsg(PersonInfoVo personInfoVo, RegionInfo regionInfo){
        System.out.println(personInfoVo.getPersonId());
        System.out.println(regionInfo.getRegionId());
        return Boolean.TRUE;
    }

}
