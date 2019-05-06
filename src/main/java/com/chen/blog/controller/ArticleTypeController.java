package com.chen.blog.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.blog.pojo.Article;
import com.chen.blog.pojo.ArticleType;
import com.chen.blog.service.IArticleService;
import com.chen.blog.service.IArticleTypeService;
import com.chen.common.ResponseObj;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-06
 */
@RestController
@RequestMapping("/blog/articleType")
public class ArticleTypeController{


	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IArticleTypeService articleTypeService;
	
	@Autowired
	private IArticleService articleSerivce;
	
	@ApiOperation(value = "查询全部")
	@GetMapping("selectAll")
	public ResponseObj<Object> selectAll() {
		try {
			List<ArticleType> list = articleTypeService.list();
			return  ResponseObj.success(list);
		} catch (Exception e) {
			log.error("查询失败 ", e);
			return ResponseObj.badRequest();
		}
	}
	
	@ApiOperation(value = "查询全部分类包含文章数量")
	@GetMapping("selectAllWithAmount")
	public ResponseObj<Object> selectAllWithAmount() {
		try {
			List<Map<String, Object>> selectAllWithAmount = articleTypeService.selectAllWithAmount();
			return  ResponseObj.success(selectAllWithAmount);
		} catch (Exception e) {
			log.error("查询失败 ", e);
			return ResponseObj.badRequest();
		}
	}
	
	@ApiOperation(value = "查询全部")
	@GetMapping("selectWithPaging")
	public ResponseObj<Object> selectWithPaging(
			 @RequestParam(name = "current", required = false) Integer current,
			 @RequestParam(name = "pagesize", required = false) Integer pagesize,
			 @RequestParam(name = "name", required = false)String name) {
		try {
			current = current == null ? 0 : current;
			pagesize = pagesize == null ? 10 : pagesize;
			
			// 添加分页数据
			Page<ArticleType> page = new Page<ArticleType>(current, pagesize);
			QueryWrapper<ArticleType> qw = new QueryWrapper<ArticleType>();
			if(StrUtil.isNotBlank(name)) {
				qw.like("name", name);
			}
			IPage<ArticleType> pageData = articleTypeService.page(page,qw);
			return ResponseObj.success( pageData);
		} catch (Exception e) {
			log.error("查询失败 ", e);
			return ResponseObj.badRequest();
		}
	}
	
	@ApiOperation(value = "保存或更新")
	@PostMapping("saveOrUpdateData")
	public ResponseObj<Object> saveOrUpdateData(
			 @RequestParam(name = "id", required = false) Integer id,
			 @RequestParam(name = "name", required = true)String name) {
		try {
			
			ArticleType type = new ArticleType();
			type.setId(id);
			type.setName(name);
			boolean saveOrUpdate = articleTypeService.saveOrUpdate(type);
			if(saveOrUpdate) {
				return ResponseObj.success();
			}else {
				return ResponseObj.badRequest();
			}
		} catch (Exception e) {
			log.error("更新失败 ", e);
			return ResponseObj.badRequest();
		}
	}
	
	@ApiOperation(value = "删除")
	@PostMapping("deleteByIds")
	public ResponseObj<Object> deleteByIds(@RequestParam(name = "ids", required = true) String ids) {
		try {
			
			List<Integer> typeIds = new ArrayList<Integer>();
			if(StrUtil.isNotBlank(ids)) {
				String[] split = ids.split(",");
				for (String id : split) {
					QueryWrapper<Article> qw = new QueryWrapper<Article>();
					qw.eq("type_id", Integer.parseInt(id));
					int count = articleSerivce.count(qw);
					if(count>0) {
						return ResponseObj.badRequest("[id:"+Integer.parseInt(id)+"]分类有被引用，无法删除！");
					}
					typeIds.add(Integer.parseInt(id));
				}
			}
			
			boolean delete = articleTypeService.removeByIds(typeIds);
			if(delete) {
				return ResponseObj.success();
			}else {
				return ResponseObj.badRequest();
			}
		} catch (Exception e) {
			log.error("删除失败 ", e);
			return ResponseObj.badRequest();
		}
		
	}
}

