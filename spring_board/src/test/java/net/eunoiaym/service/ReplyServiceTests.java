package net.eunoiaym.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.Criteria;
import net.eunoiaym.domain.ReplyCriteria;
import net.eunoiaym.domain.ReplyVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyServiceTests {
	@Setter @Autowired
	private ReplyService service;
	
//	@Test
//	public void testClass() {
//		log.info(service);
//		log.info(service.getClass().getSimpleName());
//	}
	
	@Test
	public void testExist() {
		assertNotNull(service);
	}
	
	@Test
	   public void testGetList(){
	      service.getList(new ReplyCriteria(),69626L).forEach(log::info);
	   }
	
	@Test
	public void testRegister() {
		ReplyVo replyVo = new ReplyVo();
		replyVo.setReply("서비스 테스트 등록글 제목");
		replyVo.setReplyer("서비스 테스터");
		replyVo.setBno(69626L);
		service.register(replyVo);
	}
	
	@Test
	public void testGet() {
		log.info(service.get(5L));
	}
	
	@Test
	public void testModify() {
		ReplyVo replyVo = new ReplyVo();
		replyVo.setReply("서비스 테스트 수정글 제목");
		replyVo.setReplyer("서비스 테스트 테스터");
		replyVo.setRno(5L);
		service.modify(replyVo);
	}
	
	@Test
	public void testRemove(){
		log.info(service.get(4L));
		log.info(service.remove(4L));
		log.info(service.get(4L));
	}
	
}
