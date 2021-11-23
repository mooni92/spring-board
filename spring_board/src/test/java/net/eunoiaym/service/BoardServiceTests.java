package net.eunoiaym.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.BoardVo;
import net.eunoiaym.domain.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	@Setter @Autowired
	private BoardService service;
	
	@Test
	public void testExist() {
		assertNotNull(service);
	}
	
	@Test
	public void testGetList(){
		service.getList(new Criteria()).forEach(log::info);
	}
	
	@Test
	public void testRegister() {
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle("서비스 테스트 등록글 제목");
		boardVo.setContent("서비스 테스트 등록글 내용");
		boardVo.setWriter("서비스 테스터");
		service.register(boardVo);
	}
	
	@Test
	public void testGet() {
		log.info(service.get(18L));
	}
	
	@Test
	public void testModify() {
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle("서비스 테스트 수정글 제목");
		boardVo.setContent("서비스 테스트 수정글 내용");
		boardVo.setWriter("서비스 테스터");
		boardVo.setBno(2L);
		service.modify(boardVo);
	}
	
	@Test
	public void testRemove(){
		log.info(service.get(5L));
		log.info(service.remove(5L));
		log.info(service.get(5L));
	}
	
	@Test
	public void testGetTotal() {
		log.info(service.getTotal(new Criteria()));
	}
}
