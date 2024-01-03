package com.blog.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.blog.management.model.BlogModel;
import com.blog.management.response.BasicResponseMsg;
import com.blog.management.response.BlogRequest;
import com.blog.management.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BlogController {
    
    @Autowired
    private BlogService blogService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("addBlog")
    public ResponseEntity<BasicResponseMsg> addBlog(
        @RequestParam("blog_image") MultipartFile blogImage,
        @RequestParam("blog_data") String blogData
        ){
        BasicResponseMsg responseMsg = new BasicResponseMsg();
        BlogModel blogModel = null;
        try {
            blogModel = objectMapper.readValue(blogData, BlogModel.class);
            responseMsg = blogService.addBlog(blogModel,blogImage); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
       return new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }

    @GetMapping("getBlogById/{id}")
    public ResponseEntity<BasicResponseMsg> getBlogById(@PathVariable int id){
         BasicResponseMsg responseMsg = new BasicResponseMsg();
         try {
            responseMsg = blogService.getBlogById(id);     
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }

    @GetMapping("getAllBlogs")
    public ResponseEntity<BasicResponseMsg> getAllBlogs(){
         BasicResponseMsg responseMsg = new BasicResponseMsg();
         try {
            responseMsg = blogService.getAllBlogs();     
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }

    @PostMapping("updateBlog")
    public ResponseEntity<BasicResponseMsg> updateBlog(
        @RequestParam("blog_image") MultipartFile blogImage,
        @RequestParam("blog_data") String blogData
        ){
        BasicResponseMsg responseMsg = new BasicResponseMsg();
        BlogModel blogModel = null;
        try {
            blogModel = objectMapper.readValue(blogData, BlogModel.class);
            responseMsg = blogService.updateBlog(blogModel,blogImage); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
       return new ResponseEntity<>(responseMsg,HttpStatus.OK);
    }

    @PostMapping("deleteBlogById")
    public ResponseEntity<BasicResponseMsg> deleteBlogById(@RequestBody BlogRequest blogRequest){
        System.out.println("my id: "+blogRequest.getId());
        BasicResponseMsg responseMsg = new BasicResponseMsg();
        try {
            responseMsg = blogService.deleteBlogById(blogRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseMsg, HttpStatus.OK);
    }

}
