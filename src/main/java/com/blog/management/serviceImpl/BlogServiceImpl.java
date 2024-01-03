package com.blog.management.serviceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.blog.management.dao.BlogDao;
import com.blog.management.model.BlogModel;
import com.blog.management.response.BasicResponseMsg;
import com.blog.management.response.BlogRequest;
import com.blog.management.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

    private BlogDao blogDao;

    public BlogServiceImpl(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Override
    public BasicResponseMsg addBlog(BlogModel blogModel, MultipartFile blogImage) {
        BasicResponseMsg responseMsg = new BasicResponseMsg();

        if (blogModel != null && !blogImage.isEmpty()) {
            try {
                // file upload start
                File savFile = new ClassPathResource("static/blogImages").getFile();
                Path path = Paths.get(savFile.getAbsolutePath() + File.separator + blogImage.getOriginalFilename());
                Files.copy(blogImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                // file upload end

                if (path != null) {
                    BlogModel b = new BlogModel();
                    b.setBlogTitle(blogModel.getBlogTitle());
                    b.setBlogImage(blogImage.getOriginalFilename());
                    BlogModel bModel = blogDao.save(b);

                    // setting data on base response model to display users of their response
                    responseMsg.setData(bModel); // for display all data
                    responseMsg.setMessage("Blog Added Successfully");
                    responseMsg.setStatus(200);
                } else {
                    // setting data on base response model to display users of their response
                    responseMsg.setData(null); // for display all data
                    responseMsg.setMessage("Blog Addition Failed!");
                    responseMsg.setStatus(300);
                }
            } catch (Exception e) {
                // setting data on base response model to display users of their response
                responseMsg.setData(null);
                responseMsg.setMessage("Blog Addition Failed!" + e.toString());
                responseMsg.setStatus(300);
            }
        } else {
            // setting data on base response model to display users of their response
            responseMsg.setData(null); // for display all data
            responseMsg.setMessage("Blog Addition Failed!");
            responseMsg.setStatus(300);
        }
        return responseMsg;
    }

    @Override
    public BasicResponseMsg getBlogById(int id) {
        BasicResponseMsg responseMsg = new BasicResponseMsg();
        try {
            Optional<BlogModel> findByIdListBlog = blogDao.findById(id);
            if (findByIdListBlog.isPresent()) {
                BlogModel blogModelData = findByIdListBlog.get();

                // add directory path with image start
                File savFileDirectory = new ClassPathResource("static/blogImages").getFile();
                 blogModelData.setBlogImage(savFileDirectory + File.separator + blogModelData.getBlogImage());
                // add directory path with image end

                responseMsg.setStatus(200);
                responseMsg.setMessage("Blog Data found");
                responseMsg.setData(blogModelData);
            } else {
                responseMsg.setStatus(300);
                responseMsg.setMessage("Blog Data not found");
                responseMsg.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMsg.setStatus(400);
            responseMsg.setMessage("Blog Data not found " + e.toString());
            responseMsg.setData(null);
        }
        return responseMsg;
    }

    @Override
    public BasicResponseMsg getAllBlogs() {
        BasicResponseMsg responseMsg = new BasicResponseMsg();
        try {
            List<BlogModel> allBlogs = blogDao.findAll();
            if (!allBlogs.isEmpty()) {

                // add directory path with image start
                File savFileDirectory = new ClassPathResource("static/blogImages").getFile();
                for (BlogModel blog : allBlogs) {
                    blog.setBlogImage(savFileDirectory + File.separator + blog.getBlogImage());
                }
                // add directory path with image end

                responseMsg.setStatus(200);
                responseMsg.setMessage("All blogs");
                responseMsg.setData(allBlogs);
            } else {
                responseMsg.setStatus(300);
                responseMsg.setMessage("Blogs not found");
                responseMsg.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMsg.setStatus(400);
            responseMsg.setMessage("Blogs not found " + e.toString());
            responseMsg.setData(null);
        }
        return responseMsg;
    }

    @Override
    public BasicResponseMsg updateBlog(BlogModel blogModel, MultipartFile blogImage) {
        BasicResponseMsg responseMsg = new BasicResponseMsg();

        try {
            Optional<BlogModel> checkBlogAvailability = blogDao.findById(blogModel.getBlogId());
            if (checkBlogAvailability.isPresent()) {

                // file upload start
                File savFile = new ClassPathResource("static/blogImages").getFile();
                Path path = Paths.get(savFile.getAbsolutePath() + File.separator + blogImage.getOriginalFilename());
                Files.copy(blogImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                // file upload end

                BlogModel b = checkBlogAvailability.get();
                b.setBlogTitle(blogModel.getBlogTitle());
                b.setBlogImage(blogImage.getOriginalFilename());
                blogDao.save(b);

                // setting data on base response model to display users of their response
                // responseMsg.setData(studentModel.getStudentId());
                responseMsg.setData(b); // for display all data
                responseMsg.setMessage("Blog Updated");
                responseMsg.setStatus(200);
            } else {
                responseMsg.setStatus(300);
                responseMsg.setMessage("Blog not found");
                responseMsg.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // setting data on base response model to display users of their response
            responseMsg.setData(null);
            responseMsg.setMessage("Blog Updation Failed!" + e.toString());
            responseMsg.setStatus(300);

        }
        return responseMsg;
    }

    @Override
    public BasicResponseMsg deleteBlogById(BlogRequest blogRequest) {
        BasicResponseMsg responseMsg = new BasicResponseMsg();
        try {
            Optional<BlogModel> findByIdDelBlog = blogDao.findById(blogRequest.getId());
            if (findByIdDelBlog.isPresent()) {
                BlogModel blogModelData = findByIdDelBlog.get();
                blogDao.delete(blogModelData);
                responseMsg.setStatus(200);
                responseMsg.setMessage("Blog deleted");
                responseMsg.setData(blogModelData);
            } else {
                responseMsg.setStatus(300);
                responseMsg.setMessage("No blog found!");
                responseMsg.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMsg.setStatus(400);
            responseMsg.setMessage("Blog not found " + e.toString());
            responseMsg.setData(null);
        }
        return responseMsg;
    }

}
