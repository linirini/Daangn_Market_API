package com.example.demo.src.interest;

import com.example.demo.config.BaseException;
import com.example.demo.src.interest.model.PostInterestReq;
import com.example.demo.src.interest.model.PostInterestRes;
import com.example.demo.src.post.PostProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class InterestService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final InterestDao interestDao;

    private final InterestProvider interestProvider;

    private final PostProvider postProvider;

    @Autowired
    public InterestService(InterestDao interestDao, InterestProvider interestProvider, PostProvider postProvider) {
        this.interestDao = interestDao;
        this.interestProvider = interestProvider;
        this.postProvider = postProvider;
    }

    public PostInterestRes createInterest(PostInterestReq postInterestReq) throws BaseException {
        if(postProvider.checkPostId(postInterestReq.getPostId())==0){
            throw new BaseException(GET_POST_UNKNOWN_POST_ID);
        }
        if(interestProvider.checkInterestByUserIdAndPostId(postInterestReq.getUserId(),postInterestReq.getPostId())==1){
            throw new BaseException(POST_INTEREST_ALREADY_EXIST);
        }
        if(postProvider.getPost(postInterestReq.getPostId()).getUserId()== postInterestReq.getUserId()){
            throw new BaseException(POST_INTEREST_OWN_POST);
        }
        try{
            int interestId = interestDao.createInterest(postInterestReq);
            return new PostInterestRes(interestId);
        }catch (Exception exception){
            logger.error("App - createInterest Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteInterest(int interestId) throws BaseException{
        try{
            if (interestProvider.checkInterestId(interestId) == 0) {
                throw new BaseException(DELETE_INTERESTS_UNKNOWN_INTEREST_ID);
            }
            int result = interestDao.deleteInterest(interestId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_INTEREST);
            }
        }catch(Exception exception){
            logger.error("App - deleteInterest Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
