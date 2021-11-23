package net.eunoiaym.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.eunoiaym.domain.GoodsVo;
import net.eunoiaym.mapper.GoodsMapper;

@Service
@AllArgsConstructor
public class GoodsServiceImpl implements GoodsService{
	private GoodsMapper goodsMapper;

	@Override
	public void register(GoodsVo vo) {
		goodsMapper.insert(vo);
		vo.getAttachs().forEach(goodsMapper::insertAttach);
	}
	
	
}
