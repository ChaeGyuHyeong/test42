package ksmart42.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart42.mybatis.dto.Goods;
import ksmart42.mybatis.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	private static final Logger log = LoggerFactory.getLogger(GoodsController.class);
	
	private GoodsService goodsService;
	
	public GoodsController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	
	@GetMapping("/removeGoods")
	public String removeGoods(Model model
								,@RequestParam(value="goodsCode", required = false) String goodsCode) {
		
		model.addAttribute("title", "상품삭제");
		model.addAttribute("goodsCode", goodsCode);
		
		return "merchandise/removeGoods";
	}
	
	@GetMapping("/modifyGoods")
	public String modifyGoods(Model model) {
		
		model.addAttribute("title", "상품수정");
		
		return "merchandise/modifyGoods";
	}
	
	@PostMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {
		
		goodsService.modifyGoods(goods);
		
		return "redirect:/goods/goodsList";
	}
	
	@GetMapping("/addGoods")
	public String addGoods(Model model) {
		
		model.addAttribute("title", "상품등록");
		
		return "merchandise/addGoods";
	}
	
	@PostMapping("/addGoods")
	public String addGoods(Goods goods) {
		
		log.info("상품등록 goods : {}", goods);
		
		goodsService.addGoods(goods);
		
		return "redirect:/goods/goodsList";
	}
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model, HttpSession session) {
		
		String sessionId = (String) session.getAttribute("SID");
		String sessionLevel = (String) session.getAttribute("SLEVEL");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(sessionId != null && sessionLevel.equals("2")) {
			paramMap.put("memberId", sessionId);
		}
		
		List<Goods> goodsList = goodsService.getGoodsList(paramMap);
		
		paramMap = null;
		
		model.addAttribute("title", "상품 목록");
		model.addAttribute("goodsList", goodsList);
		
		return "merchandise/goodsList";
	}
}
