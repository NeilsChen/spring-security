package com.chen.blog.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.blog.pojo.ArticleType;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-03-06
 */
public interface IArticleTypeService extends IService<ArticleType> {

	
	List<Map<String,Object>> selectAllWithAmount();
	
}
