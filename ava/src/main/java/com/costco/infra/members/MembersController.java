package com.costco.infra.members;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.costco.common.base.BaseController;
import com.costco.common.constants.Constants;

import jakarta.servlet.http.HttpSession;

@Controller
public class MembersController extends BaseController {
	@Autowired
	MembersService service;
	
	// 로그인 아이디, 비밀번호 확인용
	@ResponseBody
	@RequestMapping(value = "/memberSdmLoginConfirm")
	public Map<String, Object> memberSdmLoginConfirm(MembersDto dto, HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String loginPassword = dto.getMbrPassWord();
		
		dto = service.selectOneLogin(dto);
		if(dto != null) {
			// 비밀번호 비교
			if(matchesBcrypt(loginPassword, dto.getMbrPassWord(), 10)) {
				returnMap.put("rt", "success");
				httpSession.setMaxInactiveInterval(60 * Constants.SESSION_MINUTE_SDM); // 60second * 30 = 30minute
				httpSession.setAttribute("sessMbrSeq",   dto.getMbrSeq());
				httpSession.setAttribute("sessMbrEmail", dto.getMbrEmail());
				httpSession.setAttribute("sessMbrName",  dto.getMbrName());
			} else {
				returnMap.put("rt", "password");
			}
		} else {
			returnMap.put("rt", "id");
		}

		return returnMap;
	}
}
