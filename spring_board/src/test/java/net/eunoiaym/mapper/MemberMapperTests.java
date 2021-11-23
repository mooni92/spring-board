package net.eunoiaym.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.MemberVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberMapperTests {
	@Autowired @Setter
	private MemberMapper membermapper;
	
	@Test
	public void testRead() {
		MemberVo vo = membermapper.read("admin99");
		log.info(vo);
		vo.getAuths().forEach(log::info);
	}
	
	
}
