package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.PostPostReq;
import com.example.demo.src.post.model.PostPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PostService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostDao postDao;

    private final PostProvider postProvider;

    @Autowired
    public PostService(PostDao postDao, PostProvider postProvider) {
        this.postDao = postDao;
        this.postProvider = postProvider;
    }

    public PostPostRes createPost(PostPostReq postPostReq) throws BaseException {
        try{
            int postId = postDao.createPost(postPostReq);
            return new PostPostRes(postId);
        }catch (Exception exception){
            logger.error("App - createPost Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyPost(int postId) throws BaseException{
        try{
            if(postDao.getPost(postId)==null){
                throw new BaseException(GET_POST_UNKNOWN_POST_ID);
            }
            int result = postDao.modifyPost(postId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_POST);
            }
        }catch (Exception exception){
            logger.error("App - modifyPost Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deletePost(int postId) throws BaseException{
        try {
            if (postProvider.getPost(postId) == null) {
                throw new BaseException(DELETE_POST_UNKNOWN_POST_ID);
            }
            int result = postDao.deletePost(postId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_POST);
            }
        }catch(Exception exception){
            logger.error("App - delete post Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
