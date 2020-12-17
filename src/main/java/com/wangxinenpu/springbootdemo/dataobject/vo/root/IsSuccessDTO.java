package com.wangxinenpu.springbootdemo.dataobject.vo.root;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IS_SUCCESS")
@Data
@Accessors(chain = true)
public class IsSuccessDTO {

    @XmlAttribute(name="CODE")
    private String CODE;

    @XmlValue
    private String VALUE;

}
