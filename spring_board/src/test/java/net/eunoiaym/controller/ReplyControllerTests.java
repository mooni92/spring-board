package net.eunoiaym.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.ReplyVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
@WebAppConfiguration
public class ReplyControllerTests {
		@Autowired @Setter
		private WebApplicationContext ctx;
		private MockMvc mvc;
		
		@Before
		public void setup() {
			mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		}
		
		@Test
		public void testCreate() throws Exception {
			ReplyVo vo = new ReplyVo();
			vo.setBno(4103L);
			vo.setReply("컨트롤러 테스트 댓글");
			vo.setReplyer("테스터");
			
			String jsonStr = new Gson().toJson(vo);
			
			log.info("jsonStr :: " + jsonStr);
			
			mvc.perform(post("/replies/new")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(jsonStr))
					.andExpect(status().is(200));
		}
	}