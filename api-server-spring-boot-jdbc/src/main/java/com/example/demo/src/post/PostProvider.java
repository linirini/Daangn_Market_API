package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.GetAllPostRes;
import com.example.demo.src.post.model.GetPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PostProvider {

    private final PostDao postDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostProvider(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<GetAllPostRes> getPostsByAddress(String address) throws BaseException {
        try {
            List<GetAllPostRes> getPostResList = postDao.getPostsByAddress(address);
            return getPostResList;
        }catch (Exception exception){
            logger.error("App - getPostsByAddress Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPostRes getPost(int postId) throws BaseException{
        if (postDao.checkPostId(postId) == 0) {
            throw new BaseException(GET_POST_UNKNOWN_POST_ID);
        }
        try {
            GetPostRes getPostRes = postDao.getPost(postId);
            return getPostRes;
        } catch (Exception exception) {
            logger.error("App - getPostProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkPostId(Integer postId) throws BaseException {
        try {
            return postDao.checkPostId(postId);
        }catch (Exception exception){
            logger.error("App - checkPostId Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
