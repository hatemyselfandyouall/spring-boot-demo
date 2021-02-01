package com.wangxinenpu.springbootdemo.controller.root;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageIndexController  {
	
	@RequestMapping(value = "/index.html")
	public String list(Model model) {
		return "/page/index";

	}
	
}
