package net.eunoiaym.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data @Alias("board")
public class BoardVo {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date updateDate;
	
	private int replyCnt;
	
	private List<AttachVo> attachs = new ArrayList<>();
}
