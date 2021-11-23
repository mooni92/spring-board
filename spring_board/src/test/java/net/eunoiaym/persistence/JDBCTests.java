package net.eunoiaym.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnectioin() {
		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "BOOK_EX", "BOOK_EX")) {
			log.info(conn);
			
		}
		catch(Exception e) {
			fail(e.getMessage());
		}
	}
}
