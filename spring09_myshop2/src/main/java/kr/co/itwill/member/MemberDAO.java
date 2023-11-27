package kr.co.itwill.member;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	public MemberDAO() {
		System.out.println("-----MemberDAO() 객체생성됨");
	}

	@Autowired
	SqlSession sqlSession;
	
	public String mlevel(MemberDTO memberDto) {
		return sqlSession.selectOne("member.mlevel", memberDto);
	}
	
}//class end
