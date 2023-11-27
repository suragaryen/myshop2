package kr.co.itwill.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.co.itwill.order.OrderDTO;

@Controller
@RequestMapping("/member")
public class MemberCont {
	
	public MemberCont() {
		System.out.println("-----memberCont객체생성");
	}//member
	
	@Autowired
	private MemberDAO memberDao;
	
	@RequestMapping("/loginForm")
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/loginForm");
		
		return mav;
		
	}//list() end
	
	@PostMapping("loginProc")
	public ModelAndView loginProc(@ModelAttribute MemberDTO memberDto, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		String s_id = "";
		String s_passwd="";
		
		//member에서 id와 pw가 일치하면 mlevel 가져오기
		String mlevel = memberDao.mlevel(memberDto); //trim 언제?
		
		if(mlevel==null) {
				
			mav.addObject("msg1", "<p>아이디와 비밀번호를 다시 확인해주세요</p>");
			mav.addObject("msg2", "<p><a href='javascript:history.back()'>[다시시도]</a></p>");
			
			
		}else {
			
			session.setAttribute("s_id",s_id );
			session.setAttribute("s_passwd", s_passwd);
			session.setAttribute("mlevel", mlevel);
			
			mav.addObject("msg1", "<p>로그인 성공</p>");
		}
		
		
		return null;
		
	}//loginproc end

}//class end
