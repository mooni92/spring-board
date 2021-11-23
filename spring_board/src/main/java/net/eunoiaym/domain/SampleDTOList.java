package net.eunoiaym.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public class SampleDTOList {
	@Autowired
	private List<SampleDTO> list;
}
