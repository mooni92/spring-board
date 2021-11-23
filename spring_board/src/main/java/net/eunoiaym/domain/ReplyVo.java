package net.eunoiaym.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data @Alias("reply")
public class ReplyVo {
	private Long rno;
	private Long bno;
	
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;

}
