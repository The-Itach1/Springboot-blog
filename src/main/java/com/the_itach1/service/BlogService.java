package com.the_itach1.service;

import com.the_itach1.po.Blog;
import com.the_itach1.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    //多条件查询 title，type，isRecommend
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //单条件查询,根据Tagid查询
    public Page<Blog> listBlog(Long tagId, Pageable pageable);

    public Map<String, List<Blog>> archiveBlog();

    public Long countBlog();

    //不查询，直接返回
    Page<Blog> listBlog(Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    //根据关键词查询。
    Page<Blog> listBlog(String query, Pageable pageable);

    //获取一篇文章的信息，并且把文章内容进行markdown到html展示的转换
    Blog getAndConvert(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog );

    void deleteBlog(Long id);
}
