package net.eunoiaym.controller;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import oracle.net.ano.Service;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
// Test Controller
@WebAppConfiguration
public class BoardControllerTests {
	@Autowired @Setter
	private BoardController controller;
	
	@Autowired @Setter
	private WebApplicationContext ctx;
	private MockMvc mvc;
	
	@Before
	public void setup(){
		mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		
	}
	@Test
	public void testExist() { // test전 무조건 검사해야함.
		assertNotNull(ctx);
		assertNotNull(mvc);
		log.info(ctx);
		log.info(mvc);
	}
	
	@Test
	public void testList() throws Exception {
		ModelMap map = mvc.perform(MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "2")
				.param("amount", "4")
				)
		.andReturn()
		.getModelAndView()
		.getModelMap();
		
//		log.info(map);
		List<?> list = (List<?>)map.get("list");
		list.forEach(log :: info);
	}
	
	@Test
	public void testRegister() throws Exception {
		ModelAndView mav = mvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "컨트롤러 테스트 글 제목")
				.param("content", "컨트롤러 테스트 글 내용")
				.param("writer", "컨트롤러 테스터"))
				.andReturn()
				.getModelAndView();
		
		log.info(mav.getViewName());
		
	}
	@Test
	   public void testGet() throws Exception{
	      ModelMap map =mvc.perform(MockMvcRequestBuilders.get("/board/get").param("bno","12"))
	      .andReturn()
	      .getModelAndView()
	      .getModelMap();
	      
	      log.info(map.get("board"));
	   }

	@Test
	public void testModify() throws Exception {
		ModelAndView mav = mvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("title", "컨트롤러 테스트 글 제목 수정")
				.param("content", "컨트롤러 테스트 글 내용")
				.param("writer", "컨트롤러 테스터")
				.param("bno", "11"))
				.andReturn()
				.getModelAndView();
		
		log.info(mav.getViewName());
		
	}
	
	@Test
	public void testRemove() throws Exception {
		ModelAndView mav = mvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "24"))
				.andReturn()
				.getModelAndView();
		
		log.info(mav.getViewName());
		
	}
	
	
	
	
}
