package com.kangning.demo.service.impl;

import com.kangning.demo.dao.PersonInfoDao;
import com.kangning.demo.model.md.PersonInfoMd;
import com.kangning.demo.model.vo.PersonInfoVo;
import com.kangning.demo.service.PersonInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 加康宁 Date: 2019-04-28 Time: 16:58
 * @version $Id$
 */
@Service("personInfoService")
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public Long insertOne(PersonInfoMd personInfoMd) {
        return personInfoDao.insertOne(personInfoMd);
    }

    @Override
    public Long updateOne(PersonInfoMd personInfoMd) {
        return personInfoDao.updateOne(personInfoMd);
    }

    @Override
    public List<PersonInfoMd> queryPage(PersonInfoVo param) {
        return personInfoDao.queryPage(param);
    }
}
