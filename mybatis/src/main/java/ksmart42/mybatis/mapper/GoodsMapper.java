package ksmart42.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ksmart42.mybatis.dto.Goods;

@Mapper
public interface GoodsMapper {
	// 상품 수정
	public int modifyGoods(Goods goods);
	
	// 상품 목록 조회
	public List<Goods> getGoodsList(Map<String, Object> paramMap);
	
	// 상품 등록
	public List<Goods> addGoods(Goods goods);
}