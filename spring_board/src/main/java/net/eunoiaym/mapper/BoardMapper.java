package net.eunoiaym.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.eunoiaym.domain.BoardVo;
import net.eunoiaym.domain.Criteria;

public interface BoardMapper {
//		@Select("SELECT * FROM TBL_BOARD WHERE BNO > 0")
		public List<BoardVo> getList();
		public List<BoardVo> getListWithPaging(Criteria cri);
		void insert(BoardVo board);
		void insertSelectKey(BoardVo board); // 추가 행 갯수 출력
		BoardVo read(Long bno);
		int update(BoardVo boardVo);
		int delete(Long bno);
		int getTotalCount(Criteria cri);
		void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	}

