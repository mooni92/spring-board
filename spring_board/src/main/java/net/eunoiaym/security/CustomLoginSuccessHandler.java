package net.eunoiaym.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth)
			throws IOException, ServletException {
		log.warn("login success");
		
		String url = req.getParameter("url");
		req.getHeader("");
		
		if(url == null) {
			// referer 사용
			String referer = req.getHeader("referer");
			log.warn(referer);
		}
		else {
			// url 사용
		}
		// 

//		Set<String> roles = new HashSet<>();
//
//		auth.getAuthorities().forEach(a -> roles.add(a.getAuthority()));

//		if (roles.contains("ROLE_ADMIN")) {
//			resp.sendRedirect("/sample3/admin");
//			return;
//		}
//
//		if (roles.contains("ROLE_MEMBER")) {
//			resp.sendRedirect("/sample3/member");
//			return;
//		}
//		
//		resp.sendRedirect("/sample3/all");
	}

}
