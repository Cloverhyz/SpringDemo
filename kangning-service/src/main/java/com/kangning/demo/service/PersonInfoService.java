package com.kangning.demo.service;

import com.kangning.demo.model.md.PersonInfoMd;
import com.kangning.demo.model.vo.PersonInfoVo;
import java.util.List;

/**
 * @author 加康宁 Date: 2019-04-28 Time: 16:45
 * @version $Id$
 */
public interface PersonInfoService {

    Long insertOne(PersonInfoMd personInfoMd);

    Long updateOne(PersonInfoMd personInfoMd);

    List<PersonInfoMd> queryPage(PersonInfoVo param);

}
