package net.eunoiaym.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data 
public class GoodsVo {
	private Integer bno;
	private String title;
	private Integer price;
	private String brand;
	private List<Map<String, Object>> attachs = new ArrayList<>();
	
}
