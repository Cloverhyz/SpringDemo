package com.kangning.demo.model.md;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kangning.demo.model.vo.PersonName;

/**
 * @author 加康宁 Date: 2019-07-01 Time: 20:14
 * @version $Id$
 */
public class PersonRealMd extends PersonInfoMd<PersonName> {

    private static final long serialVersionUID = -4290329157118552362L;

    @JsonDeserialize
    private PersonName personName;

    @Override
    public PersonName getPersonName() {
        return personName;
    }

    @Override
    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

}
