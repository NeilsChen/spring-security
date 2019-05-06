package com.chen.blog.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.blog.dao.IArticleDao;
import com.chen.blog.pojo.Article;
import com.chen.blog.service.IArticleService;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-05
 */
@Service
@DS("blog")
public class ArticleServiceImpl extends ServiceImpl<IArticleDao, Article> implements IArticleService {

	@Autowired
	private IArticleDao articleDao;

	@Override
	public List<Integer> selectIdsByTagId(Integer tagId) {
		return articleDao.selectIdsByTagId(tagId);
	}

	@Override
	public List<Article> selectByParams(Integer start, Integer pagesize, String title, Integer typeId, Integer tagId, Integer status,String orderBy, Integer asc) {
		
		return articleDao.selectByParams(start,pagesize, title, typeId, tagId, status,orderBy,asc);
	}

	@Transactional
	@Override
	public boolean saveOrUpdateData(Article article, String tagIds) {
		boolean flag = false;
		if (article.getId() == null) {
			article.setCreateTime(new Date());
			flag = articleDao.insert(article) > 0 ? true : false;
		} else {
			article.setUpdateTime(new Date());
			article.setAuthor(null);
			flag = articleDao.updateById(article) > 0 ? true : false;
			articleDao.deleteAllTagsById(article.getId());
		}
		if (StrUtil.isNotBlank(tagIds)) {
			String[] split = tagIds.split(",");
			List<String> tagIdList = Arrays.asList(split);
			articleDao.insertNewTags(article.getId(), tagIdList);
		}
		return flag;
	}

	@Transactional
	@Override
	public boolean deleteByIds(List<Integer> articleIds) {
		articleDao.deleteArticleTagByAids(articleIds);
		int deleteBatchIds = articleDao.deleteBatchIds(articleIds);
		return deleteBatchIds > 0 ? true : false;
	}

	@Override
	public int selectCountByParams(String title, Integer typeId, Integer tagId, Integer status) {
		return articleDao.selectCountByParams(title, typeId, tagId, status);
	}

}
