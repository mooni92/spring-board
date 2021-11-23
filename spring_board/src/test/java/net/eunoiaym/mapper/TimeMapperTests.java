package net.eunoiaym.mapper;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.persistence.DataSourceTests;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {
	@Autowired @Setter
	private TimeMapper mapper;
	
	@Test
	public void testExist() {
		assertNotNull(mapper);
		log.info(mapper);
	}
	
	@Test
	public void testGetTime() {
		log.info(mapper.getTime());
	}
	
	@Test
	public void testGetTime2() {
		String time = mapper.getTime2();
		log.trace(time);
		log.debug(time);
		log.info(time);
		log.warn(time);
		log.error(time);
		log.fatal(time);
		
	}
}
