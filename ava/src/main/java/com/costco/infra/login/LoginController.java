package com.costco.infra.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.costco.common.constants.Constants;

@Controller
public class LoginController {
	@Value("${my_email}")
	private String myEmail;
	
	@Autowired
	LoginService service;
	
	// 관리자 로그인
	@RequestMapping(value = "/loginCostco")
	public String loginCostco(Model model) throws Exception {
		model.addAttribute("myEmail", myEmail);
		return Constants.PATH_LOGIN + "loginCostco";
	}	
}
