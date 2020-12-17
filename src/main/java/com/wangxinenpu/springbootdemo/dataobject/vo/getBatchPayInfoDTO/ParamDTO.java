package com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PARAM")
@Data
@Accessors(chain = true)
public class ParamDTO {
     
	 @XmlAttribute(name="NAME")
	 private String NAMES;
	 
	 @XmlValue
	 private String VALUE;


	 
	 
}
