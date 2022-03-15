package ksmart42.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart42.mybatis.dto.Member;
import ksmart42.mybatis.dto.MemberLevel;

@Mapper
public interface MemberMapper {
	// 상품코드에 따른 구매이력 삭제
	public int removeOrderBySellerId(String memberId);
	
	// 판매자가 등록한 상품 삭제
	public int removeGoodsBySellerId(String memberId);
	
	// 구매자가 구매한 이력 삭제
	public int removerOrder(String memberId);
	
	// 회원의 로그인 이력을 삭제
	public int removeLoginHistory(String memberId);
	
	// 회원 삭제
	public int removeMember(String memberId);
	
	// 회원 수정
	public int modifyMember(Member member);
	
	// 아이디별 회원정보 조회
	public Member getMemberInfoById(String memberId);
	
	// 회원 아이디 중복체크
	public boolean isIdCheck(String memberId);
	
	// 회원 등급 목록 조회
	public List<MemberLevel> getMemberLevelList();
	
	// 회원 전체 목록 조회
	public List<Member> getMemberList();
	
	// 회원 가입
	public int addMember(Member member);
}
