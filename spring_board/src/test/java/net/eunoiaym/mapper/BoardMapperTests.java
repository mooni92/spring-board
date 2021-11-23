package net.eunoiaym.mapper;

import org.apache.ibatis.annotations.Mapper;
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
public class BoardMapperTests {
	@Setter @Autowired
	private BoardMapper mapper;
	
	@Test
	public void tetGetList() {
		mapper.getList().forEach(log::info);
//		mapper.getList();
	}
	
	@Test
	public void tetGetListPaging() {
		Criteria cri =  new Criteria();
//		cri.setType("T");
//		cri.setKeyword("수정");
		
		mapper.getListWithPaging(cri).forEach(log::info);
//		mapper.getList();
	}
	
	@Test
	public void testInsert(){
		BoardVo board = new BoardVo();
		board.setTitle("영속 테스트 제목");
		board.setContent("영속 테스트 내용");
		board.setWriter("영속테스터");
		mapper.insert(board);
	}
	
	@Test
	public void testInsertSelectKey(){
		BoardVo board = new BoardVo();
		board.setTitle("영속 테스트 제목");
		board.setContent("영속 테스트 내용");
		board.setWriter("영속테스터");
		log.info("before :: " +  board);
		mapper.insertSelectKey(board);
		log.info("after :: " + board);
	}
	
	@Test
	public void testRead() {
		log.info(mapper.read(10L));
		
	}
	
	@Test
	public void testUpdate() {
		BoardVo board = new BoardVo();
		board.setTitle("수정된 영속 테스트 제목");
		board.setContent("수정된 영속 테스트 내용");
		board.setWriter("수정된 영속테스터");
		board.setBno(10L);
		log.info(mapper.update(board));
		log.info(mapper.read(10L));
	}
	
	@Test
	public void testDelete() {
		log.info(mapper.read(3L));
		log.info(mapper.delete(3L));
		log.info(mapper.read(3L));
		
	}
	
	@Test
	public void testGetTotalCount() {
		Criteria cri = new Criteria();
		cri.setType("T");
		cri.setKeyword("테스트");
		log.info(mapper.getTotalCount(cri));
	}
}

