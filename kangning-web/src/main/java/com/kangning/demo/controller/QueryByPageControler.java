package com.kangning.demo.controller;

import com.kangning.demo.model.md.PersonInfoMd;
import com.kangning.demo.model.vo.PersonInfoVo;
import com.kangning.demo.service.PersonInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 加康宁 Date: 2019-04-30 Time: 12:03
 * @version $Id$
 */
@Controller
@RequestMapping("queryByPageControler")
public class QueryByPageControler {

    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping("testQueryPage")
    public void testQueryPage(){
        PersonInfoVo personInfoVo = new PersonInfoVo();
        personInfoVo.setPage(2);
        personInfoVo.setPageSize(30);
        List<PersonInfoMd> personInfoList = personInfoService.queryPage(personInfoVo);
        System.out.println(personInfoVo);
        System.out.println(personInfoList);
    }

    @RequestMapping("testInsert")
    public void testInsert(){
        for (int i = 0; i < 100; i++){
            PersonInfoMd personInfoMd = new PersonInfoMd();
            personInfoMd.setPersonAge(i);
            personInfoMd.setPersonName("小明" + i + "号");
            System.out.println("插入结果" + personInfoService.insertOne(personInfoMd));
        }
    }
}
