package net.eunoiaym.sample;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


// spring-core
// spring-test
// junit

// spring-core
// aspectweaver
// aspectjrt

// spring-core
// spring-mybatis
// mybatis

// spring-core
// spring-quartz
// quartz

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleTests {
	@Setter @Autowired
	private Restaurant restaurant;
	static final Logger logger = Logger.getLogger(SampleTests.class);
	
	@Test 	// junit
	public void testExist() {
		assertNotNull(restaurant);
		log.info(restaurant);
		log.info("==================================");
		log.info(restaurant.getChef());
		
		logger.info(restaurant);
	}
}
