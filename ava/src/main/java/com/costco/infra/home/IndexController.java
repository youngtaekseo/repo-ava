package com.costco.infra.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.costco.common.constants.Constants;

@Controller
public class IndexController {
	
	// 관리자메인
	@RequestMapping(value = "/index")
	public String indexSdm() throws Exception {
		return Constants.PATH_HOME + "index";
	}
}
