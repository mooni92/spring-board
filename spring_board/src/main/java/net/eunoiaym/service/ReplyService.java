package net.eunoiaym.service;

import java.util.List;

import net.eunoiaym.domain.ReplyCriteria;
import net.eunoiaym.domain.ReplyVo;

public interface ReplyService {
	void register(ReplyVo vo);
	ReplyVo get(Long rno);
	boolean modify(ReplyVo vo);
	boolean remove(Long rno);
	List<ReplyVo> getList(ReplyCriteria cri, Long bno);
}
