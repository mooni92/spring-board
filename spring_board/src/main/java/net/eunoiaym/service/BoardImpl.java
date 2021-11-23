package net.eunoiaym.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.eunoiaym.domain.AttachVo;
import net.eunoiaym.domain.BoardVo;
import net.eunoiaym.domain.Criteria;
import net.eunoiaym.mapper.AttachMapper;
import net.eunoiaym.mapper.BoardMapper;

@Service
@AllArgsConstructor
public class BoardImpl implements BoardService {
	private BoardMapper boardMapper;
	private AttachMapper attachMapper;
	
	@Override @Transactional
	public void register(BoardVo boardVo) {
		boardMapper.insertSelectKey(boardVo);
		
		boardVo.getAttachs().forEach(attach->{
			attach.setBno(boardVo.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVo get(Long bno) {
		return boardMapper.read(bno);
	}

	@Override @Transactional
	public boolean modify(BoardVo boardVo) {
		boolean result = boardMapper.update(boardVo) > 0;
		
		attachMapper.deleteAll(boardVo.getBno());
		if(result) {
			boardVo.getAttachs().forEach(vo-> {
				vo.setBno(boardVo.getBno());
				attachMapper.insert(vo);
			});
		}
		
		return result;
	}

	@Override @Transactional
	public boolean remove(Long bno) {
		attachMapper.deleteAll(bno);
		return boardMapper.delete(bno) >0 ;
	}

	@Override
	public List<BoardVo> getList(Criteria cri) {
		return boardMapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}

	@Override
	public List<AttachVo> getAttachs(Long bno) {
		return attachMapper.findByBno(bno);
	}

}
