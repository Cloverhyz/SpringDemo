package com.kangning.demo.mapper;

import com.kangning.demo.framework.mybatis.SqlMapper;
import com.kangning.demo.model.md.PersonInfoMd;
import com.kangning.demo.model.vo.PersonInfoVo;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author 加康宁 Date: 2019-04-23 Time: 11:56
 * @version $Id$
 */
@Repository("personInfoMapper")
public interface PersonInfoMapper extends SqlMapper {

    /**
     * 插入测试
     * @return
     */
    Long insertOne(PersonInfoMd personInfoMd);

    Long updateOne(PersonInfoMd personInfoMd);

    List<PersonInfoMd> queryPage(PersonInfoVo param);

}
