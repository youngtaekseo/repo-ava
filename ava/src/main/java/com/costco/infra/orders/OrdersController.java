package com.costco.infra.orders;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.costco.common.constants.Constants;
import com.costco.common.util.UtilDateTime;
import com.costco.common.util.UtilFunction;

@Controller
public class OrdersController {
	@Autowired
	OrdersService service;
	
	// 검색조건 초기화
	@RequestMapping(value = "/orderListInit")
	public String orderListInit(@ModelAttribute("vo") OrdersVo vo, Model model) throws Exception {
		vo.setShDateEnd(null);
		vo.setShDateStart(null);
		vo.setShDelNy(0);
		vo.setShOption(null);
		vo.setShOptionDate(0);
		vo.setShValue(null);
		vo.setShReleasedNy(null);
		return "redirect:/orderList";
	}
	
	// 주문 리스트
	@RequestMapping(value = "/orderList")
	public String ordersList(@ModelAttribute("vo") OrdersVo vo, OrdersDto dto, Model model)throws Exception {
		UtilFunction.setSearch(vo);
		
		int rowCount = service.getCount(vo);
		
		if(rowCount > 0) {			
			vo.setPagingVo(rowCount);
			model.addAttribute("list", service.selectList(vo));
		};
		
		return Constants.PATH_ORDERS + "orderList";
	}
	
	//	주문 페이징리스트
	@RequestMapping(value = "/orderListAjax")
	public String orderListAjax(@ModelAttribute("vo") OrdersVo vo, OrdersDto dto, Model model)throws Exception {
		UtilFunction.setSearch(vo);
		
		int rowCount = service.getCount(vo);
		
		if(rowCount > 0) {			
			vo.setPagingVo(rowCount);
			model.addAttribute("list", service.selectList(vo));
		};
		
		return Constants.PATH_ORDERS + "orderListAjax";
	}
	
	// 검색조건 초기화
	@RequestMapping(value = "/orderDetailListInit")
	public String orderDetailListInit(@ModelAttribute("vo") OrdersVo vo, Model model) throws Exception {
		vo.setShDateEnd(null);
		vo.setShDateStart(null);
		vo.setShDelNy(0);
		vo.setShOption(null);
		vo.setShOptionDate(0);
		vo.setShValue(null);
		vo.setShReleasedNy(null);
		return "redirect:/orderDetailList";
	}
	
	// 상세주문리스트
	@RequestMapping(value = "/orderDetailList")
	public String orderDetailList(@ModelAttribute("vo") OrdersVo vo, OrdersDto dto, Model model)throws Exception {
		UtilFunction.setSearch(vo);
		
		int rowCount = service.getCountOrt(vo);
		
		if(rowCount > 0) {			
			vo.setPagingVo(rowCount);
			
			model.addAttribute("list", service.selectListOrt(vo));
		};
		
		return Constants.PATH_ORDERS + "orderDetailList";
	}
	
	//	상세주문 리스트 페이징
	@RequestMapping(value = "/orderDetailListAjax")
	public String orderDetailListAjax(@ModelAttribute("vo") OrdersVo vo, OrdersDto dto, Model model)throws Exception {
		UtilFunction.setSearch(vo);
		
		int rowCount = service.getCountOrt(vo);
		
		if(rowCount > 0) {			
			vo.setPagingVo(rowCount);
			
			model.addAttribute("list", service.selectListOrt(vo));
		};
		
		return Constants.PATH_ORDERS + "orderDetailListAjax";
	}
	
	//	상세주문등록화면
	@RequestMapping(value = "/orderDetailCreate")
	public String orderDetailCreate(OrdersDto dto, Model model) throws Exception {
		model.addAttribute("list", service.clientNameList(dto));
		model.addAttribute("pdtlist", service.productList(dto));
		return Constants.PATH_ORDERS + "orderDetailCreate";
	}
	
	//	주문수정화면
	@RequestMapping(value = "/orderForm")
	public String orderForm(OrdersDto dto, Model model) throws Exception {
		model.addAttribute("itemOrd", service.selectOneOrd(dto));
		return Constants.PATH_ORDERS + "orderForm";
	}
	
	
	//	상세주문수정화면
	@RequestMapping(value = "/orderDetailForm")
	public String orderDetailForm(OrdersDto dto, OrdersDto afDto, Model model) throws Exception {
		afDto = service.selectOne(dto);
		afDto.setOrtReleaseDt(UtilDateTime.nowString());
		model.addAttribute("item", afDto);
		
		model.addAttribute("list", service.clientNameList(dto));
		model.addAttribute("pdtlist", service.productList(dto));
		model.addAttribute("courierList", service.courierServiceList(dto));
		return Constants.PATH_ORDERS + "orderDetailForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/orderCountCheck")
	public Map<String, Object> orderCountCheck(OrdersVo vo, OrdersDto dto) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		dto = service.selectOneOrderCountCheck(vo);
		returnMap.put("rt","success");
		returnMap.put("orderCountCheck", dto.getXortCount()); 
		return returnMap;
	}
	
	//	주문등록
	@RequestMapping(value = "/orderInsert")
	public String orderInsert(OrdersDto dto) throws Exception {
		service.insertOrd(dto);
		return "redirect:/orderList";
	}
	
	//	상세주문등록
	@RequestMapping(value = "/ortInsert")
	public String ortInsert(OrdersDto dto) throws Exception {
		service.insertOrt(dto);
		return "redirect:/orderList";
	}
	
	//	상세주문수정
	@RequestMapping(value = "/updateOrt")
	public String updateOrt(OrdersDto dto, OrdersDto isDto) throws Exception {
		service.updateOrt(dto);
		
		// 출고상태 변경		
		isDto = service.selectOneOrtReleaseNy(dto);
		
		// 주문, 주문상세 출고상태 설정
		if(isDto != null) {
			if(isDto.getXorderdetail_0() == 0) {
				dto.setOrdReleasedNy(0); // 미입고
			} else if(isDto.getXordortCount() == isDto.getXorderdetail_0()) {
				dto.setOrdReleasedNy(1); // 입고완료
			} else {
				dto.setOrdReleasedNy(2); // 부분입고
			};
			
			// 주문상세 출고상태 변경
			service.updateOrdReleasedNy(dto);			
		}
		
		return "redirect:/orderDetailList";
	}
	
	//	주문 삭제 
	@RequestMapping(value= "/orderdelete")
	public String orderdelete(OrdersDto dto)throws Exception {
		//service.myorderdeletechile(dto);
		service.orderdelete(dto);
		return "redirect:orderList";
	}
	
	//	주문 다중 삭제
	@ResponseBody
	@RequestMapping(value = "/orderListDelete")
	public Map<String, Object> orderListDelete(OrdersDto dto, OrdersVo vo) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		//service.myorderDetailListDeletechile(vo);
		if(service.orderListDelete(vo)>0)
		{
			service.orderdeletechile(dto);
			returnMap.put("rt", "success");
		}else {
			returnMap.put("rt", "fail");
		}

		return returnMap;
	}
	
	//	주문 ny 변경 
	@RequestMapping(value = "/orderSelNY")
	public String orderSelNY(OrdersDto dto)throws Exception {
		service.orderSelNY(dto);
		return "redirect:orderList";
	}


	//	주문 디테일 삭제
	@RequestMapping(value = "/orderdetaildelete")
	public String orderdetaildelete(OrdersDto dto) throws Exception {
		service.orderdetaildelete(dto);
		return "redirect:/orderDetailList";
	}
	
	//	주문 디테일 ny 변경
	@RequestMapping(value ="orderDetailSelNY")
	public String orderDetailSelNY(OrdersDto dto) throws Exception {
		service.orderDetailSelNY(dto);
		return "redirect:/orderDetailList";
	}
	
	//	주문 디테일 다중 삭제
	@ResponseBody
	@RequestMapping(value = "/orderDetailListDelete")
	public Map<String, Object> orderDetailListDelete(OrdersVo vo) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if(service.orderDetailListDelete(vo) > 0) {
			returnMap.put("rt", "success");
		} else {
			returnMap.put("rt", "fail");
		}

		return returnMap;
	}	
}
