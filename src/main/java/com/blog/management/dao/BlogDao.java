package com.blog.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.management.model.BlogModel;

public interface BlogDao extends JpaRepository<BlogModel,Integer> {
    
}
