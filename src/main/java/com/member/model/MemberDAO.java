package com.member.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO implements MemberDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
		"INSERT INTO member (member_email,member_name,member_password,member_birthday,member_gender,member_phone,member_address)"
		+ " VALUES (?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT =
		"SELECT member_id,member_email,member_name,member_password,member_birthday,member_gender,member_phone,member_address FROM member order by member_id";
	private static final String GET_ONE_STMT = 
		"SELECT member_id,member_email,member_name,member_password,member_birthday,member_gender,member_phone,member_address FROM member where member_id = ?";
	private static final String DELETE = 
		"DELETE FROM member where member_id = ?";
	private static final String UPDATE = 
		"UPDATE member set member_email=?, member_name=?, member_password=?, member_birthday=?, member_gender=?, member_phone=?, member_address=? where member_id = ?";
	
	@Override
	public void insert(MemberVO memberVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memberVO.getMemberEmail());
			pstmt.setString(2, memberVO.getMemberName());
			pstmt.setString(3, memberVO.getMemberPassword());
			pstmt.setDate(4, memberVO.getMemberBirthday());
			pstmt.setInt(5, memberVO.getMemberGender());
			pstmt.setString(6, memberVO.getMemberPhone());
			pstmt.setString(7, memberVO.getMemberAddress());
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "	+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void update(MemberVO memberVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, memberVO.getMemberEmail());
			pstmt.setString(2, memberVO.getMemberName());
			pstmt.setString(3, memberVO.getMemberPassword());
			pstmt.setDate(4, memberVO.getMemberBirthday());
			pstmt.setInt(5, memberVO.getMemberGender());
			pstmt.setString(6, memberVO.getMemberPhone());
			pstmt.setString(7, memberVO.getMemberAddress());
			pstmt.setInt(8, memberVO.getMemberId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se){
			throw new RuntimeException("A database error occured. "+ se.getMessage());			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void delete(Integer memberId) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, memberId);
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}		
	}
	
	@Override
	public MemberVO findByPrimaryKey(Integer memberId) {
		
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("member_id"));
				memberVO.setMemberEmail(rs.getString("member_email"));
				memberVO.setMemberName(rs.getString("member_name"));
				memberVO.setMemberPassword(rs.getString("member_password"));
				memberVO.setMemberBirthday(rs.getDate("member_birthday"));
				memberVO.setMemberGender(rs.getInt("member_gender"));
				memberVO.setMemberPhone(rs.getString("member_phone"));
				memberVO.setMemberAddress(rs.getString("member_address"));
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return memberVO;	
	}
	
	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("member_id"));
				memberVO.setMemberEmail(rs.getString("member_email"));
				memberVO.setMemberName(rs.getString("member_name"));
				memberVO.setMemberPassword(rs.getString("member_password"));
				memberVO.setMemberBirthday(rs.getDate("member_birthday"));
				memberVO.setMemberGender(rs.getInt("member_gender"));
				memberVO.setMemberPhone(rs.getString("member_phone"));
				memberVO.setMemberAddress(rs.getString("member_address"));
				list.add(memberVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}
