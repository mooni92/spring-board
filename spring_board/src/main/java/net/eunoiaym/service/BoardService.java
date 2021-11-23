package net.eunoiaym.service;

import java.util.List;

import net.eunoiaym.domain.AttachVo;
import net.eunoiaym.domain.BoardVo;
import net.eunoiaym.domain.Criteria;

public interface BoardService {
	void register(BoardVo boardVo);
	BoardVo get(Long bno);
	boolean modify(BoardVo boardVo);
	boolean remove(Long bno);
	List<BoardVo> getList(Criteria cri);
	int getTotal(Criteria cri);
	List<AttachVo> getAttachs(Long bno);
}
