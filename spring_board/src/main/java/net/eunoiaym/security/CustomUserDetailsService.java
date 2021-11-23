package net.eunoiaym.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.MemberVo;
import net.eunoiaym.mapper.MemberMapper;
import net.eunoiaym.security.domain.CustomUser;

@Log4j
@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired @Setter
	private MemberMapper mapper;
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		log.warn(arg0);
		MemberVo vo = mapper.read(arg0);
		return vo == null ? null : new CustomUser(vo);
	}
	
}
