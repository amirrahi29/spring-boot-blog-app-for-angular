package com.blog.management.service;

import org.springframework.web.multipart.MultipartFile;

import com.blog.management.model.BlogModel;
import com.blog.management.response.BasicResponseMsg;
import com.blog.management.response.BlogRequest;

public interface BlogService {

    BasicResponseMsg addBlog(BlogModel blogModel, MultipartFile blogImage);

    BasicResponseMsg getBlogById(int id);

    BasicResponseMsg getAllBlogs();

    BasicResponseMsg updateBlog(BlogModel blogModel, MultipartFile blogImage);

    BasicResponseMsg deleteBlogById(BlogRequest blogRequest);
    
}
