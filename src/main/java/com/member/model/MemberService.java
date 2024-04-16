package com.member.model;

import java.util.List;

public class MemberService {

	private MemberDAO_interface dao;
	
	public MemberService() {
		dao = new MemberDAO();
	}
	
	public MemberVO addMember(String memberEmail, String memberName, String memberPassword, java.sql.Date memberBirthday,
			Integer memberGender, String memberPhone, String memberAddress) {
		
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemberEmail(memberEmail);
		memberVO.setMemberName(memberName);
		memberVO.setMemberPassword(memberPassword);
		memberVO.setMemberBirthday(memberBirthday);
		memberVO.setMemberGender(memberGender);
		memberVO.setMemberPhone(memberPhone);
		memberVO.setMemberAddress(memberAddress);
		dao.insert(memberVO);
		
		return memberVO;
	}
	
	public MemberVO updateMember(Integer memberId,String memberEmail, String memberName, String memberPassword, java.sql.Date memberBirthday,
			Integer memberGender, String memberPhone, String memberAddress) {
		
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemberId(memberId);
		memberVO.setMemberEmail(memberEmail);
		memberVO.setMemberName(memberName);
		memberVO.setMemberPassword(memberPassword);
		memberVO.setMemberBirthday(memberBirthday);
		memberVO.setMemberGender(memberGender);
		memberVO.setMemberPhone(memberPhone);
		memberVO.setMemberAddress(memberAddress);
		dao.update(memberVO);
		
		return dao.findByPrimaryKey(memberId);
	}
	
	public void deleteMember(Integer memberId) {
		dao.delete(memberId);
	}
	
	public MemberVO getOneMember(Integer memberId) {
		return dao.findByPrimaryKey(memberId);
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}
	
}
