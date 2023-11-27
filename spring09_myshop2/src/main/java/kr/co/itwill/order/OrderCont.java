package kr.co.itwill.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderCont {
	
    public OrderCont() {
        System.out.println("-----OrderCont()객체생성됨");
    }//end
    
    
    @Autowired
    OrderDAO orderDao;
    
    
    @GetMapping("/orderform")
    public String orderForm() {
    	return "/order/orderForm";
    }//orderForm() end
    
    
    @PostMapping("/insert")
    public ModelAndView orderInsert(@ModelAttribute OrderDTO orderDto, HttpSession session) throws MessagingException {
    	
    	ModelAndView mav = new ModelAndView();
    	
    	//String s_id=session.getAttribute("세션변수명"); 실제 구현
    	String s_id="ola"; //테스트용 임시 아이디
    	
    	//System.out.println(orderDto.toString());
    	
    	
    	//1) 주문서번호 생성하기
    	//   예) 최초주문 202311231436151
    	//       있으면  202311231436152
    	
    	//오늘날짜 및 현재시각을 문자열 "년월일시분초"로 구성해서 반환하기
    	//->예)20231123143615
    	SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
    	String cdate = sd.format(new Date());
    	System.out.println(cdate);
    	
    	String orderno = orderDao.orderno(cdate);
    	if(orderno.equals("1")) { //최초주문
    		orderno = cdate + "1"; //"20231123143615" + "1"
    	}else {
    		int n=Integer.parseInt(orderno.substring(14))+1; //맨마지막글자 + 1
    		orderno = cdate + n;
    	}//if end
    	
    	System.out.println(orderno);
    	
    	//2) 총결제금액 구하기
    	int totalamount=orderDao.totalamount(s_id);
    	System.out.println(totalamount);
    	
    	
    	//3) orderDto에 주문서번호, 총결제금액, 세션아이디 추가로 담기
    	orderDto.setOrderno(orderno);
    	orderDto.setTotalamount(totalamount);
    	orderDto.setId(s_id);
    	
    	//System.out.println(orderDto.toString());
    	
    	
    	//4) orderlist테이블에 3)의 내용으로 행 추가하기
    	int cnt = orderDao.orderlistInsert(orderDto); 
    	if(cnt==1) {
    		
    		//5) cart테이블에 있는 주문상품을 orderdetail테이블에 옮겨 담기
    		HashMap<String, String> map = new HashMap<>();
    		map.put("orderno", orderno);
    		map.put("s_id", s_id);
		
    		int result = orderDao.orderdetailInsert(map);

    		if(result!=0) { 	
    			
    			//6) cart테이블 비우기
    			orderDao.cartDelete(s_id);
    			
    			
    			//7) 주문내역서 메일 보내기 : 과제
    			//1) 사용하고자 하는 메일서버에서 인증받은 게정과 비번 등록하기
				//	-> MyAuthenticator 클래스 생성  
				//2)메일서버(POP3/SMTP) 지정하기
				
				
				String mailServer = "mw-002.cafe24.com"; //메일서버
				Properties props = new Properties();
				props.put("mail.smtp.host", mailServer);
				props.put("mail.smtp.auth", true);
				
				//3)메일서버에서 인증받은 계정+비번
				Authenticator myAuth = new MyAuthenticator();
				
				//4) 2)와 3)이 유효한지 검증
				Session sess = Session.getInstance(props, myAuth);
				//out.print("메일 서버 인증 성공!!");
				
				//5 메일 보내기
				
				String to		="jennet7@naver.com";
				String from		="myShop1127@naver.com";
				String subject 	=s_id+"님 MyShop 주문 현황";
				String content  ="";
				
				//엔터 및 특수문자 변경
				//content=Utility.convertChar(content);
				
				//표작성
				content += "<hr>";
				content += "<table border='1'>";
				content += "<tr>";
				content += "	 <th>주문이</th>";
				content += "	 <th>정상적으로</th>";
				content += "	 <th>완료되었습니다!</th>";
				content += "	 <th>축하합니다!!</th>";
				content += "</tr>";
				
				
				//받는 사람 이메일 주소
				InternetAddress[] address = {new InternetAddress(to)};
															
				/*  
					수신인이 여러명일 경우
					InternetAddress[] address = { new InternetAddress(to1),
												  new InternetAddress(to2),
												  new InternetAddress(to3), ~~~};													};
				*/
				//메일 관련 정보 작성
				Message msg = new MimeMessage(sess);
				
				
				//받는 사람
				msg.setRecipients(Message.RecipientType.TO, address);
				//참조	   Message.RecipientType.CC
				//숨은참조   Message.RecipientType.BCC
				
				//보내는 사람
				msg.setFrom(new InternetAddress(from));
				//메일 제목
				msg.setSubject(subject);
				
				//메일 내용
				msg.setContent(content, "text/html; charset=UTF-8");
				
				//메일 보낸날짜
				msg.setSentDate(new Date());
				
				
				//메일 전송
				Transport.send(msg);
				
				//out.print(to+"님에게 메일이 발송되었습니다");
    			
        		mav.addObject("msg1", "<img src='../images/logo_itwill.png'>");
        		mav.addObject("msg2", "<p>주문이 완료되었습니다</p>");    		
        		mav.addObject("msg3", "<p><a href='/product/list'>[계속쇼핑하기]</a></p>");
    		}else {
    			mav.addObject("msg1", "<img src='../images/fail.png'>");
        		mav.addObject("msg2", "<p>주문이 실패되었습니다</p>");
        		mav.addObject("msg3", "<p><a href='javascript:history.back()'>[다시시도]</a></p>");
    		}//if end 
    		
    	}else {
    		mav.addObject("msg1", "<img src='../images/fail.png'>");
    		mav.addObject("msg2", "<p>주문이 실패되었습니다</p>");
    		mav.addObject("msg3", "<p><a href='javascript:history.back()'>[다시시도]</a></p>");
    	}//if end
    	
    	mav.setViewName("/order/msgView"); // /WEB-INF/views/order/msgView.jsp
    	return mav;
    }//orderInsert() end
    
    
    
    
    
}//class end
