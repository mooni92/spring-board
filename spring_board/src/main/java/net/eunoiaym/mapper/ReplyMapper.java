package net.eunoiaym.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.eunoiaym.domain.ReplyCriteria;
import net.eunoiaym.domain.ReplyVo;

public interface ReplyMapper {
	int insert(ReplyVo vo);
	ReplyVo read(Long rno);
	int update(ReplyVo vo);
	int delete(Long bno);
	List<ReplyVo> getList(@Param("bno") Long bno, @Param("cri") ReplyCriteria cri);
	
}
