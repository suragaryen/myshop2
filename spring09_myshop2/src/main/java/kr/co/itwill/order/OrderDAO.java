package kr.co.itwill.order;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAO {

    public OrderDAO() {
        System.out.println("-----OrderDAO()객체생성됨");
    }//end
    
    @Autowired
    SqlSession sqlSession;
    
    
    public String orderno(String cdate) {
    	return sqlSession.selectOne("order.orderno", cdate);
    }//orderno() end
    
    public int totalamount(String s_id) {
    	return sqlSession.selectOne("order.totalamount", s_id);
    }//totalamount end()
    
    public int orderlistInsert(OrderDTO dto) {
    	return sqlSession.insert("order.orderlistInsert", dto);
    }//orderlistInsert() end
    
    public int orderdetailInsert(HashMap<String, String> map) {
    	return sqlSession.insert("order.orderdetailInsert", map);
    }//orderdetailInsert() end
    
    public int cartDelete(String s_id) {
    	return sqlSession.delete("order.cartDelete", s_id);
    }//cartDelete() end
    
}//class end
