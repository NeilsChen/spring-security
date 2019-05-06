package com.chen.blog.dao;

import com.chen.blog.pojo.Article;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-05
 */
public interface IArticleDao extends BaseMapper<Article> {

	@Select("SELECT article_id FROM blog_article_tag_relation WHERE tag_id=#{tagId}")
	List<Integer> selectIdsByTagId(@Param("tagId") Integer tagId);

	List<Article> selectByParams(@Param("start") Integer start, @Param("pagesize") Integer pagesize, @Param("title") String title,
			@Param("typeId") Integer typeId, @Param("tagId") Integer tagId, @Param("status") Integer status,
			@Param("orderBy") String orderBy, @Param("asc") Integer asc);

	int selectCountByParams( @Param("title") String title, @Param("typeId") Integer typeId, @Param("tagId") Integer tagId, @Param("status") Integer status);
	
	@Delete("DELETE from blog_article_tag_relation where article_id=#{id}")
	int deleteAllTagsById(Integer id);

	int deleteArticleTagByAids(@Param("articleIds") List<Integer> articleIds);

	int insertNewTags(@Param("articleId") Integer id, @Param("tagIdList") List<String> tagIdList);

}
