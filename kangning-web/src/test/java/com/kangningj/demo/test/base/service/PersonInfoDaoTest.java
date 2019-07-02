package com.kangningj.demo.test.base.service;

import com.kangning.demo.dao.PersonInfoDao;
import com.kangning.demo.model.md.PersonInfoMd;
import com.kangning.demo.model.md.PersonRealMd;
import com.kangning.demo.model.vo.PersonInfoVo;
import com.kangning.demo.model.vo.PersonName;
import com.kangningj.demo.test.base.CommonTest;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 加康宁 Date: 2019-07-01 Time: 19:16
 * @version $Id$
 */
public class PersonInfoDaoTest extends CommonTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void personNameInsertTest(){
        PersonInfoMd personInfoMd = new PersonInfoMd();
        personInfoMd.setCreateTime(new Date());
        personInfoMd.setPersonAge(1);
        PersonName personName = new PersonName();
        personName.setHoseName("加");
        personName.setName("云1");
        personInfoMd.setPersonName(personName);
        Long id = personInfoDao.insertOne(personInfoMd);
        System.out.println(personInfoMd.getPersonId());
    }


    @Test
    public void personNameReadTest(){
        PersonInfoVo personInfoVo = new PersonInfoVo();
        personInfoVo.setPersonId(101L);
        List<PersonInfoMd> personInfoMds = personInfoDao.queryPage(personInfoVo);
        PersonRealMd personRealMd = (PersonRealMd) personInfoMds.get(0);
        System.out.println(personRealMd.getPersonName());
    }

}
