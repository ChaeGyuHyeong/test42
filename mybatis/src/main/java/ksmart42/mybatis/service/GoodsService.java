package ksmart42.mybatis.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart42.mybatis.dto.Goods;
import ksmart42.mybatis.mapper.GoodsMapper;

@Service
@Transactional
public class GoodsService {
	// DI 의존성 주입
	private GoodsMapper goodsMapper;
	
	public GoodsService(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}
	
	public int modifyGoods(Goods goods) {
		return goodsMapper.modifyGoods(goods);
	}
	
	/**
	 * 상품등록
	 * @author ksmart42 채규형
	 * @param goods
	 */
	public void addGoods(Goods goods) {
		goodsMapper.addGoods(goods);
	}
	
	public List<Goods> getGoodsList(Map<String, Object> paramMap) {
		List<Goods> goodsList = goodsMapper.getGoodsList(paramMap);
		return goodsList;
	}
	
}
