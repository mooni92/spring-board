package net.eunoiaym.mapper;

import java.util.Map;

import net.eunoiaym.domain.MemberVo;

public interface MemberMapper {
	void insertMember(Map<String, Object> map);
	void insertAuth(Map<String, Object> map);
	MemberVo read(String userid);
}
