package net.eunoiaym.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.eunoiaym.domain.BoardVo;
import net.eunoiaym.domain.Criteria;
import net.eunoiaym.domain.ReplyCriteria;
import net.eunoiaym.domain.ReplyVo;
import net.eunoiaym.mapper.BoardMapper;
import net.eunoiaym.mapper.ReplyMapper;

@AllArgsConstructor @Service
public class ReplyServiceImpl implements ReplyService{

	private ReplyMapper mapper;
	private BoardMapper boardMapper;
	
	@Override
	@Transactional
	public void register(ReplyVo vo) {
		// 작업1
		mapper.insert(vo);
		// 작업2
		boardMapper.updateReplyCnt(vo.getBno(),	1);
	}

	@Override
	public ReplyVo get(Long rno) {
		return mapper.read(rno);
	}

	@Override
	public boolean modify(ReplyVo vo) {
		return mapper.update(vo) > 0;
	}

	@Override
	@Transactional
	public boolean remove(Long rno) {
		boardMapper.updateReplyCnt(get(rno).getBno(), -1);
		return mapper.delete(rno) > 0;
	}

	 @Override
	   public List<ReplyVo> getList(ReplyCriteria cri, Long bno) {
	      return mapper.getList(bno, cri);
	   }
}
