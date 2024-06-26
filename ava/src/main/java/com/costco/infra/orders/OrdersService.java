package com.costco.infra.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

	@Autowired
	OrdersDao dao;
	
//	주문조회
	public List<OrdersDto> selectList(OrdersVo vo) {
		return dao.selectList(vo);
	}
//	주문count
	public int getCount(OrdersVo vo) {
		return dao.getCount(vo);
	}
//	상세주문조회
	public List<OrdersDto> selectListOrt(OrdersVo vo) {
		return dao.selectListOrt(vo);
	}
//	상세주문count
	public int getCountOrt(OrdersVo vo) {
		return dao.getCountOrt(vo);
	}
//	주문항목생성 시작
	public OrdersDto selectOne(OrdersDto dto) {
		return dao.selectOne(dto);
	}
//	주문항목수정
	public OrdersDto selectOneOrd(OrdersDto dto) {
		return dao.selectOneOrd(dto);
	}
//	주문추가
	public int insertOrt(OrdersDto dto) {
		return dao.insertOrt(dto);
	}
//	상세주문추가
	public int insertOrd(OrdersDto dto) {
		return dao.insertOrd(dto);
	}
//	주문등록 및 수정 고객사
	public List<OrdersDto> clientNameList(OrdersDto dto) {
		return dao.clientNameList(dto);
	}
//	주문등록 및 수정 제품
	public List<OrdersDto> productList(OrdersDto dto) {
		return dao.productList(dto);
	}
//	주문등록 및 수정 배송사
	public List<OrdersDto> courierServiceList(OrdersDto dto) {
		return dao.courierServiceList(dto);
	}
//	주문수정
	public int updateOrd(OrdersDto dto) {
		return dao.updateOrd(dto);
	}
//	상세주문 수정
	public int updateOrt(OrdersDto dto) {
		return dao.updateOrt(dto);
	}
//	주문 출고상세 상태
	public OrdersDto selectOneOrtReleaseNy(OrdersDto dto) {
		return dao.selectOneOrtReleaseNy(dto);
	}
//	주문 출고여부 변경
	public int updateOrdReleasedNy(OrdersDto dto) {
		return dao.updateOrdReleasedNy(dto);
	}
	

//	주문 삭제 
	public int orderdelete(OrdersDto dto)
	{
		return dao.orderdelete(dto);
	}	
//	주문 다중 삭제
	public int orderListDelete(OrdersVo vo)
	{
		return dao.orderListDelete(vo);
	}
//	주문 ny변경
	public int orderSelNY(OrdersDto dto)
	{
		return dao.orderSelNY(dto);
	}
//	주문 삭제시 자식 요소 삭제
	public int orderdeletechile(OrdersDto dto)
	{
		return dao.orderdeletechile(dto);
	}
//	주문 체크된 자식요소 삭제
	public int orderDetailListDeletechile(OrdersVo vo)
	{
		return dao.orderDetailListDeletechile(vo);
	}

	
//	주문 디테일 삭제
	public int orderdetaildelete(OrdersDto dto)
	{
		return dao.orderdetaildelete(dto);
	}
//	주문 디테일 다중 삭제 
	public int orderDetailListDelete(OrdersVo vo)
	{
		return dao.orderDetailListDelete(vo);
	}
//	주문 디테일 ny변경 
	public int orderDetailSelNY(OrdersDto dto)
	{
		return dao.orderDetailSelNY(dto);
	}	
	
	// 주문가능수량확인
	public OrdersDto selectOneOrderCountCheck(OrdersVo vo) {
		return dao.selectOneOrderCountCheck(vo);
	}
}
