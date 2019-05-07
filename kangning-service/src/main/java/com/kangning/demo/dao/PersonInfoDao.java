package com.kangning.demo.dao;

import com.kangning.demo.model.md.PersonInfoMd;
import com.kangning.demo.model.vo.PersonInfoVo;
import java.util.List;

/**
 * @author
 * @version ${Id}
 * @date 2018-07-19 Time: 18:13
 */

public interface PersonInfoDao {

    /**
     * 插入测试
     * @return
     */
    Long insertOne(PersonInfoMd personInfoMd);
    
    Long updateOne(PersonInfoMd personInfoMd);

    List<PersonInfoMd> queryPage(PersonInfoVo param);
}
