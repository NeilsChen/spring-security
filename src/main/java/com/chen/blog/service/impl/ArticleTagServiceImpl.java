package com.chen.blog.service.impl;

import com.chen.blog.pojo.ArticleTag;
import com.chen.blog.dao.IArticleTagDao;
import com.chen.blog.service.IArticleTagService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class ArticleTagServiceImpl extends ServiceImpl<IArticleTagDao, ArticleTag> implements IArticleTagService {

}
