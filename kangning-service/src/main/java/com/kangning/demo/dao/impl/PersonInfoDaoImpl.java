package com.kangning.demo.dao.impl;

import com.kangning.demo.dao.PersonInfoDao;
import com.kangning.demo.mapper.PersonInfoMapper;
import com.kangning.demo.model.md.PersonInfoMd;
import com.kangning.demo.model.vo.PersonInfoVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-25 Time: 11:56
 */
@Repository("personInfoDao")
public class PersonInfoDaoImpl implements PersonInfoDao {

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Override
    public Long insertOne(PersonInfoMd personInfoMd) {
        return personInfoMapper.insertOne(personInfoMd);
    }

    @Override
    public Long updateOne(PersonInfoMd personInfoMd) {
        return personInfoMapper.updateOne(personInfoMd);
    }

    @Override
    public List<PersonInfoMd> queryPage(PersonInfoVo param) {
        return personInfoMapper.queryPage(param);
    }
}
