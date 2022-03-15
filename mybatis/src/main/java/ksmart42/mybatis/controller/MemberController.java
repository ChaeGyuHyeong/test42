package ksmart42.mybatis.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksmart42.mybatis.dto.Member;
import ksmart42.mybatis.dto.MemberLevel;
import ksmart42.mybatis.mapper.MemberMapper;
import ksmart42.mybatis.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	// DI 의존성 주입 생성자 메서드 주입방식
	private MemberService memberService;
	private MemberMapper memberMapper;
	
	public MemberController(MemberService memberService, MemberMapper memberMapper) {
		this.memberService = memberService;
		this.memberMapper = memberMapper;
	}
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/member/login";
	}
	
	@GetMapping("/login")
	public String login(Model model
						,@RequestParam(value="result", required = false) String result) {
		
		model.addAttribute("title", "회원로그인");
		
		if(result != null) model.addAttribute("result", result);
		
		return "member/login";
	}
	
	@PostMapping("/login")
	public String login(Member member, HttpSession session, RedirectAttributes reAttr) {
		String memberId = member.getMemberId();
		String memberPw = member.getMemberPw();
		
		Member checkMember = memberMapper.getMemberInfoById(memberId);
		
		if(checkMember != null && checkMember.getMemberPw() != null && memberPw.equals(checkMember.getMemberPw())) {
			String sessionName = checkMember.getMemberName();
			String sessionLevel = checkMember.getMemberLevel();
			
			session.setAttribute("SID", 	memberId);
			session.setAttribute("SNAME", 	sessionName);
			session.setAttribute("SLEVEL",	sessionLevel);
			
			log.info("로그인 성공");
			
			return "redirect:/";
		}
		
		reAttr.addAttribute("result", "등록된 회원이 없습니다.");
		
		return "redirect:/member/login";
	}
	
	/**
	 * 회원탈퇴처리
	 */
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(name="memberId", required = false) String memberId
							   ,@RequestParam(name="memberPw", required = false, defaultValue = "") String memberPw
							   ,RedirectAttributes reAttr) {
		
		log.info("회원탈퇴처리 memberId: {}", memberId);
		log.info("회원탈퇴처리 memberPw: {}", memberPw);
		
		Member member = memberMapper.getMemberInfoById(memberId);
		
		if(member != null && member.getMemberPw() != null && memberPw.equals(member.getMemberPw())) {
			memberService.removeMember(member);
		
			log.info("회원탈퇴 성공");
			
			return "redirect:/member/memberList";
		}
		reAttr.addAttribute("memberId", memberId);
		reAttr.addAttribute("result", "회원의 정보가 일치하지 않습니다.");
		log.info("회원탈퇴 실패");
		/* /member/removeMember?memberId=001&result=회원의 정보가 일치하지 않습니다. */
		return "redirect:/member/removeMember";
	}
	
	/**
	 * 회원탈퇴화면
	 */
	@GetMapping("/removeMember")
	public String removeMember(Model model
								,@RequestParam(name="memberId", required = false) String memberId
								,@RequestParam(name="result", required = false) String result) {
		
		model.addAttribute("title", "회원탈퇴화면");
		model.addAttribute("memberId", memberId);
		
		if(result != null) model.addAttribute("result", result);
		
		return "member/removeMember";
	}
	
	/**
	 * 회원수정처리
	 */
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		log.info("회원 수정화면에서 입력받은 값 : {}", member);
		memberService.modifyMember(member);
		return "redirect:/member/memberList";
	}
	
	/**
	 * 회원 수정화면
	 */
	@GetMapping("/modifyMember")
	public String modifyMember(Model model
							   ,@RequestParam(name="memberId", required = false) String memberId
							   ,@RequestParam(name="memberName", required = false) String memberName) {
		log.info("회원 수정화면 폼 쿼리스트링 memberId : {}", memberId);
		log.info("회원 수정화면 폼 쿼리스트링 memberName : {}", memberName);
		
		Member member = memberService.getMemberInfoById(memberId);
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원수정 화면");
		model.addAttribute("member", member);
		model.addAttribute("levelList", memberLevelList);
		
		return "member/modifyMember";
	}
	
	/**
	 * idCheck ajax
	 * @RequestParam(value = "memberId") == request.getParameter("memberId");
	 */
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean isIdCheck(@RequestParam(value = "memberId") String memberId) {
		boolean idCheck = false;
		log.info("아이디중복체크 클릭시 요청받은 memberId의 값 : {}", memberId);
		boolean result = memberMapper.isIdCheck(memberId);
		if(result) idCheck = true;
		log.info("아이디중복체크 여부 : {}", result);
		return idCheck;
	}
	
	/**
	 * 회원가입 폼
	*/
	@GetMapping("/addMember")
	public String addMember(Model model) {
		
		//회원등급 목록 데이터
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원가입");
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/addMember";
	}

	/**
	 * /addMember method 방식이 다르기 때문에 주소를 중복해서 사용가능
	 * @param member(회원가입 폼 전송시 요소의 name과 dto의 멤버변수의 이름과 같으면 자동으로 바인딩하는 객체)
	 * 		   커맨드객체
	 * @return redirect: -> request.sendRedirect("")
	*/
	@PostMapping("/addMember")
	public String addMember(Member member) {
		
		log.info("회원가입폼에서 입력받은 데이터: {}", member);
		
		memberService.addMember(member);
		
		return "redirect:/member/memberList";
	}
	
	@GetMapping("/memberList")
	public String getMemberList(Model model) {
		
		log.info("회원목록 요청");
		
		List<Member> memberList = memberService.getMemberList();
		
		model.addAttribute("title", "회원목록조회");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";
	}
	
}
