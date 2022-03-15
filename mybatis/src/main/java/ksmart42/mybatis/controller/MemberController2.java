package ksmart42.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exMember")
public class MemberController2 {
	/*
	 * @GetMapping("/memberList2") 
	 * public String getMemberList(Model model) {
	 * 
	 * return "member/memberList"; 
	 * }
	 */
	
	/**
	 * member/addMember2 -> 회원가입 폼을 사용자 요청할 때의 주소
	 * return -> 프로젝트 내부의 웹자원 결로
	 */
	@GetMapping("/addMember2")
	public String addMember(Model model) {
		
		model.addAttribute("title", "회원가입");
		
		return "exMember/addMember2";
	}
	
}