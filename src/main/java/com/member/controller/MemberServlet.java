package com.member.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.member.model.*;

public class MemberServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) {
			// 來自select_page.jsp的請求
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String str = req.getParameter("memberId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.put("memberId","請輸入會員編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			Integer memberId = null;
			try {
				memberId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.put("memberId","會員編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.開始查詢資料*****************************************/
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMember(memberId);
			if (memberVO == null) {
				errorMsgs.put("memberId","查無資料");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
			req.setAttribute("memberVO", memberVO); // 資料庫取出的memberVO物件,存入req
			String url = "/member/listOneMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMember.jsp
			successView.forward(req, res);
		}
			
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllMember.jsp的請求

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				Integer memberId = Integer.valueOf(req.getParameter("memberId"));
				
				/***************************2.開始查詢資料****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memberId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				String param = "?memberId="  +memberVO.getMemberId()+
						       "&memberEmail="  +memberVO.getMemberEmail()+
						       "&memberName="    +memberVO.getMemberName()+
						       "&memberPassword="+memberVO.getMemberPassword()+
						       "&memberBirthday="    +memberVO.getMemberBirthday()+
						       "&memberGender="   +memberVO.getMemberGender()+
						       "&memberPhone="   +memberVO.getMemberPhone()+
						       "&memberAddress=" +memberVO.getMemberAddress();
				String url = "/member/update_member_input.jsp"+param;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_member_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { // 來自update_member_input.jsp的請求
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer memberId = Integer.valueOf(req.getParameter("memberId").trim());
				
				String memberEmail = req.getParameter("memberEmail");
				String memberEmailReg = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
				if (memberEmail == null || memberEmail.trim().length() == 0) {
					errorMsgs.put("memberEmail","會員信箱: 請勿空白");
				} else if(!memberEmail.trim().matches(memberEmailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("memberEmail","會員信箱: 只能是英文字母、數字和@. , 且長度必需大於2");
	            }
				
				String memberName = req.getParameter("memberName");
				String memberNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (memberName == null || memberName.trim().length() == 0) {
					errorMsgs.put("memberName","會員姓名: 請勿空白");
				} else if(!memberName.trim().matches(memberNameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("memberName","會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String memberPassword = req.getParameter("memberPassword").trim();
				String memberPasswordReg = "^[(a-zA-Z0-9)]{8,}$";
				if (memberPassword == null || memberPassword.trim().length() == 0) {
					errorMsgs.put("memberPassword","會員密碼: 請勿空白");
				} else if(!memberPassword.trim().matches(memberPasswordReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("memberPassword","會員密碼: 英文字母、數字 , 且長度必需大於8");
	            }
				
				java.sql.Date memberBirthday = null;
				try {
					memberBirthday = java.sql.Date.valueOf(req.getParameter("memberBirthday").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("memberBirthday","請輸入日期");
				}
				
				Integer memberGender = null;
				try {
					memberGender = Integer.valueOf(req.getParameter("memberGender"));
				} catch (NumberFormatException e) {
					errorMsgs.put("memberGender","請選擇性別");
				}
				
				String memberPhone = req.getParameter("memberPhone").trim();
				String memberPhoneReg = "^[(0-9)]{10}$";
				if (memberPhone == null || memberPhone.trim().length() == 0) {
					errorMsgs.put("memberPhone","會員手機: 請勿空白");
				} else if(!memberPhone.trim().matches(memberPhoneReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("memberPhone","會員手機: 只能是數字, 且長度必需等於10");
	            }
				
				String memberAddress = req.getParameter("memberAddress").trim();
				if (memberAddress == null || memberAddress.trim().length() == 0) {
					errorMsgs.put("memberAddress","地址請勿空白");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/update_member_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.updateMember(memberId, memberEmail, memberName, memberPassword, memberBirthday,memberGender, memberPhone, memberAddress);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memberVO", memberVO); // 資料庫update成功後,正確的的memberVO物件,存入req
				String url = "/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMember.jsp
				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addMember.jsp的請求  
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);

				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			
			String memberEmail = req.getParameter("memberEmail");
			String memberEmailReg = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
			if (memberEmail == null || memberEmail.trim().length() == 0) {
				errorMsgs.put("memberEmail","會員信箱: 請勿空白");
			} else if(!memberEmail.trim().matches(memberEmailReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.put("memberEmail","會員信箱: 只能是英文字母、數字和@. , 且長度必需大於2");
            }
			
			String memberName = req.getParameter("memberName");
			String memberNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (memberName == null || memberName.trim().length() == 0) {
				errorMsgs.put("memberName","會員姓名: 請勿空白");
			} else if(!memberName.trim().matches(memberNameReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.put("memberName","會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
            }
			
			String memberPassword = req.getParameter("memberPassword").trim();
			String memberPasswordReg = "^[(a-zA-Z0-9)]{8,}$";
			if (memberPassword == null || memberPassword.trim().length() == 0) {
				errorMsgs.put("memberPassword","會員密碼: 請勿空白");
			} else if(!memberPassword.trim().matches(memberPasswordReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.put("memberPassword","會員密碼: 英文字母、數字 , 且長度必需大於8");
            }
			
			java.sql.Date memberBirthday = null;
			try {
				memberBirthday = java.sql.Date.valueOf(req.getParameter("memberBirthday").trim());
			} catch (IllegalArgumentException e) {
				errorMsgs.put("memberBirthday","請輸入日期");
			}
			
			Integer memberGender = null;
			try {
				memberGender = Integer.valueOf(req.getParameter("memberGender"));
			} catch (NumberFormatException e) {
				errorMsgs.put("memberGender","請選擇性別");
			}
			
			String memberPhone = req.getParameter("memberPhone").trim();
			String memberPhoneReg = "^[(0-9)]{10}$";
			if (memberPhone == null || memberPhone.trim().length() == 0) {
				errorMsgs.put("memberPhone","會員手機: 請勿空白");
			} else if(!memberPhone.trim().matches(memberPhoneReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.put("memberPhone","會員手機: 只能是數字, 且長度必需等於10");
            }
			
			String memberAddress = req.getParameter("memberAddress").trim();
			if (memberAddress == null || memberAddress.trim().length() == 0) {
				errorMsgs.put("memberAddress","地址請勿空白");
			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/member/addMember.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
				
				/***************************2.開始新增資料***************************************/
				MemberService memberSvc = new MemberService();
				memberSvc.addMember(memberEmail, memberName, memberPassword, memberBirthday,memberGender, memberPhone, memberAddress);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer memberId = Integer.valueOf(req.getParameter("memberId"));
				
				/***************************2.開始刪除資料***************************************/
				MemberService memberSvc = new MemberService();
				memberSvc.deleteMember(memberId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
		
	}
}
