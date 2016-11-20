package com.aire.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testIndexjsp {
	@RequestMapping("/click")
	public String click() {
		System.out.println("success");
		System.out.println("返回");
		return "success";
	}
}
