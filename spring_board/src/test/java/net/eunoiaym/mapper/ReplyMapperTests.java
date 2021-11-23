package net.eunoiaym.mapper;

import static org.junit.Assert.assertNotNull;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.ReplyCriteria;
import net.eunoiaym.domain.ReplyVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	@Setter @Autowired
	private ReplyMapper mapper;
	
	@Test
	public void testExist() {
		assertNotNull(mapper);
	}
	
	@Test
	public void testInsert() {
		IntStream.range(0,5).forEach(i->{
			ReplyVo vo = new ReplyVo();
			
			vo.setBno(69637L);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("댓글러");
			
			mapper.insert(vo);
		});
	}
	
	@Test
	public void testRead() {
		log.info(mapper.read(3L));
		
	}
	
	@Test
	public void testUpdate() {
		ReplyVo reply = new ReplyVo();
		reply.setReply("수정된 댓글 테스트 내용");
		reply.setReplyer("수정된 댓글테스터");
		reply.setRno(80L);
		log.info(mapper.update(reply));
		log.info(mapper.read(10L));
	}
	
	@Test
	public void testRemove() {
		log.info(mapper.read(81L));
		log.info(mapper.delete(81L));
		log.info(mapper.read(81L));
		
	}
	
	@Test
	public void testGetList() {
		ReplyCriteria criteria = new ReplyCriteria();
//		criteria.setLastRno(11L);
		mapper.getList(69637L, criteria).forEach(log::info);
	}
}

