package kr.co.itwill.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartCont {
	
	public CartCont() {
		System.out.println("-----CartCont()객체생성됨🐸");
	}//end
	
	@Autowired
	CartDAO cartDao;
	
	@PostMapping("/insert")
	public String cartInsert(@ModelAttribute CartDTO cartDto, HttpSession session) {
			//로그인 기능을 구현했다면 session.getAttribute() 활용
			//cartDTo.setId(session.getAttribute("s_id"));
		cartDto.setId("ola"); //여기서는 임시로 "itwill"
		cartDao.cartInsert(cartDto);
		return "redirect:/cart/list"; //장바구니 목록페이지 호출
	}//cartInsert() end
	
	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		//로그인 했다면 
		//String s_id=session.getAttribute("s_id")
		String s_id="ola"; //테스트용 임시 아이디 "itwill"
		String filename = "";
		int product_code;
		
		
		ModelAndView mav = new ModelAndView();
		
		
		mav.addObject("list", cartDao.cartList(s_id));
		mav.addObject("fileImg",cartDao.filename(s_id));
		mav.setViewName("cart/list"); //WEB-INF/views/cart/list.jsp
		
		//System.out.println(cartDao.cartList(s_id));
		//System.out.println(cartDao.filename(s_id));					
		
		return mav;
		
	}//list() end
	
	@GetMapping("/delete")
	public String delete(int cartno, HttpSession session) {
		//delete from cart where cartno=? and id=?
		/*
			CartDTO cartDto = new CartDTO();
			cartDto.setCartno(cartno);
			cartDto.setId(session.getAttribute("세션변수명"));
			cartDao.cartDelete(cartDto);
		*/
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("no", cartno);
		//map.put("s_id", session.getAttribute("세션변수명"));
		map.put("s_id", "itwill");
		cartDao.cartDelete(map);
		return "redirect:/cart/list";
	}//delete() end
	
	
}//class end
