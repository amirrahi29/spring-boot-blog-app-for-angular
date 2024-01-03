package com.blog.management.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogs")
public class BlogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private int blogId;

    @Column(name = "blog_image")
    private String blogImage;

    @Column(name = "blog_title")
    private String blogTitle;

    @CreationTimestamp
    @Column(name = "blog_created")
    private LocalDateTime blogCreated;

    public BlogModel(int blogId, String blogImage, String blogTitle, LocalDateTime blogCreated) {
        this.blogId = blogId;
        this.blogImage = blogImage;
        this.blogTitle = blogTitle;
        this.blogCreated = blogCreated;
    }

    public BlogModel() {
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public LocalDateTime getBlogCreated() {
        return blogCreated;
    }

    public void setBlogCreated(LocalDateTime blogCreated) {
        this.blogCreated = blogCreated;
    }

}
