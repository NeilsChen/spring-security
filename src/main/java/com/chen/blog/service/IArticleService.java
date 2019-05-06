package com.chen.blog.service;

import com.chen.blog.pojo.Article;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-05
 */
public interface IArticleService extends IService<Article> {

	/**
	 * 根据标签id查询文章ids
	 * @param tagId
	 * @return 
	 */
	List<Integer> selectIdsByTagId(Integer tagId);
	
	List<Article> selectByParams(Integer start, Integer pagesize, String title, Integer typeId, Integer tagId, Integer status, String orderBy, Integer asc);
	
	
	int selectCountByParams( String title, Integer typeId, Integer tagId,Integer status);
	
	/**
	 * 添加或修改
	 * @return
	 */
	boolean saveOrUpdateData(Article article, String tagIds);
	
	
	boolean deleteByIds(List<Integer> articleIds);
}
