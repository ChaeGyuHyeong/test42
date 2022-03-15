package ksmart42.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart42.mybatis.dto.Member;
import ksmart42.mybatis.dto.MemberLevel;
import ksmart42.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {

	// DI 의존성 주입 생성자 메서드 주입방식
	private MemberMapper memberMapper;
	
	@Autowired
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	public int modifyMember(Member member) {
		
		return memberMapper.modifyMember(member);
	}
	
	public Member getMemberInfoById(String memberId) {
		
		return memberMapper.getMemberInfoById(memberId);
	}
	
	public List<Member> getMemberList(){
		
		List<Member> memberList = memberMapper.getMemberList();
		
		return memberList;
	}
	
	public int addMember(Member member) {
		
		int result = memberMapper.addMember(member);
		
		return result;
	}

	public List<MemberLevel> getMemberLevelList() {
		
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		
		return memberLevelList;
	}

	public void removeMember(Member member) {
		//권한
		String memberLevel = member.getMemberLevel();
		String memberId = member.getMemberId();
		
		//권한별로 삭제 프로세스
		//판매자
		if("2".equals(memberLevel)) {
			
			memberMapper.removeOrderBySellerId(memberId);
			
			memberMapper.removeGoodsBySellerId(memberId);
			
		}
		//구매자
		else if("3".equals(memberLevel)) {
			
			memberMapper.removerOrder(memberId);
			
		}
		//공통사항 삭제 프로세스
		memberMapper.removeLoginHistory(memberId);
		
		memberMapper.removeMember(memberId);
		
	}
	
}
