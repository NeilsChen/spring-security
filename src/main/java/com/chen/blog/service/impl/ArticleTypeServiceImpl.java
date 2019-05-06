package com.chen.blog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.blog.dao.IArticleTypeDao;
import com.chen.blog.pojo.ArticleType;
import com.chen.blog.service.IArticleTypeService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-06
 */
@Service
@DS("blog")
public class ArticleTypeServiceImpl extends ServiceImpl<IArticleTypeDao, ArticleType> implements IArticleTypeService {

	@Autowired
	private IArticleTypeDao articleTypeDao;
	
	@Override
	public List<Map<String, Object>> selectAllWithAmount() {
		return articleTypeDao.selectAllWithAmount();
	}

}
