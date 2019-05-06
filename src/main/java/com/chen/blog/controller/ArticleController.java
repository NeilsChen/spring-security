package com.chen.blog.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chen.blog.pojo.Article;
import com.chen.blog.service.IArticleService;
import com.chen.common.ResponseObj;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-05
 */
@RestController
@RequestMapping("/blog/article")
public class ArticleController{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IArticleService articleService;
	
	@ApiOperation(value = "通过id查询")
	@GetMapping("selectById") //@RequestParam(name = "id", required = true) 
	public ResponseObj<Object> selectById(@ApiParam( value = "主键id", required = true, example = "123") @RequestParam(name = "id", required = true)  Integer id) {
		try {
			Article byId = articleService.getById(id);
			return new ResponseObj<Object>(HttpServletResponse.SC_OK,"操作成功！", byId);
		} catch (Exception e) {
			log.error("操作失败！", e);
			return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST, "操作失败！");
		}
	}
	
	@ApiOperation(value = "分页查询")
	@GetMapping("selectWithPaging")
	public ResponseObj<Object> selectWithPaging( 
			 @RequestParam(name = "current", required = false) Integer current,
			 @RequestParam(name = "pagesize", required = false) Integer pagesize,
			 @RequestParam(name = "title", required = false)  String title,
			 @RequestParam(name = "typeId", required = false)  Integer typeId,
			 @RequestParam(name = "tagId", required = false)  Integer tagId,
			 @RequestParam(name = "status", required = false)  Integer status,
			 @RequestParam(name = "orderBy", required = false)  String orderBy,
			 @RequestParam(name = "asc", required = false)  Integer asc) {
		try {

			current = current == null ? 1 : current;
			pagesize = pagesize == null ? 10 : pagesize;
			
			int start = (current-1)*pagesize;
			int count = articleService.selectCountByParams(title, typeId, tagId, status);
			List<Article> listData = articleService.selectByParams(start,pagesize, title, typeId, tagId, status,orderBy,asc);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("total", count);
			map.put("records", listData);
			
			return new ResponseObj<Object>(HttpServletResponse.SC_OK, "操作成功！", map);
		} catch (Exception e) {
			log.error("操作失败！", e);
			return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST, "操作失败！");
		}
	}
	
	@ApiOperation(value = "添加或更新数据")
	@PostMapping("saveOrUpdateData")
	public ResponseObj<Object> saveOrUpdateData(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "title", required = true) String title,
			@RequestParam(name = "content", required = false) String content,
			@RequestParam(name = "typeId", required = true) Integer typeId,
			@RequestParam(name = "tagIds", required = false) String tagIds,
			@RequestParam(name = "author", required = false) Integer author,
			@RequestParam(name = "status", required = false) Integer status,
			@RequestParam(name = "coverImg", required = false) String coverImg) {
		try {
			Article article = new Article();
			article.setId(id);
			article.setAuthor(author);
			article.setTitle(title);
			article.setContent(content);
			article.setTypeId(typeId);
			article.setStatus(status);
			article.setCoverImg(coverImg);
			boolean save = articleService.saveOrUpdateData(article,tagIds);
			if(save) {
				return new ResponseObj<Object>(HttpServletResponse.SC_OK, "操作成功！");
			}else {
				return new ResponseObj<Object>(203, "数据保存失败！");
			}
		} catch (Exception e) {
			log.error("操作失败！", e);
			return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST, "操作失败！");
		}
	}
	
	
	@ApiOperation(value = "增加阅读量")
	@PostMapping("addReadAmount") //@RequestParam(name = "id", required = true) 
	public ResponseObj<Object> addReadAmount(@ApiParam( value = "主键id", required = true, example = "123") @RequestParam(name = "id", required = true)  Integer id) {
		try {
			Article byId = articleService.getById(id);
			byId.setReadAmount(byId.getReadAmount()+1);
			boolean updateById = articleService.updateById(byId);
			if(updateById) {
				return new ResponseObj<Object>(HttpServletResponse.SC_OK, "操作成功！");
			}else {
				return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST, "操作失败！");
			}
		} catch (Exception e) {
			log.error("操作失败！", e);
			return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST,"操作失败！");
		}
	}
	
	
	@ApiOperation(value = "删除数据")
	@PostMapping("deleteByIds")
	public ResponseObj<Object> deleteByIds(@RequestParam(name = "ids", required = false) String ids) {
		try {
			
			List<Integer> articleIds = new ArrayList<Integer>();
			if(StrUtil.isNotBlank(ids)) {
				String[] split = ids.split(",");
				for (String id : split) {
					articleIds.add(Integer.parseInt(id));
				}
			}
			boolean delete = articleService.deleteByIds(articleIds);
			if(delete) {
				return new ResponseObj<Object>(HttpServletResponse.SC_OK, "操作成功！");
			}else {
				return new ResponseObj<Object>(203, "数据删除失败！");
			}
		} catch (Exception e) {
			log.error("操作失败！", e);
			return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST, "操作失败！");
		}
	}
	
	
}

