package net.eunoiaym.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Component
@AllArgsConstructor // 단일 생성자
@NoArgsConstructor
public class Restaurant {  // >> 인스턴스 생성
	@Autowired
	private Chef chef;  // 포함  >>인스턴스 생성
}
