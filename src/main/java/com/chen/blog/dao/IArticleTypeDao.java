package com.chen.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.blog.pojo.ArticleType;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-06
 */
public interface IArticleTypeDao extends BaseMapper<ArticleType> {

	@Select("SELECT t.id,t.name,(SELECT COUNT(*) FROM blog_article WHERE type_id=t.id) AS num FROM blog_article_type t")
	List<Map<String,Object>> selectAllWithAmount();
	
	
}
