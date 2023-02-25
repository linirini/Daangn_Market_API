package com.example.demo.src.interest;

import com.example.demo.config.BaseException;
import com.example.demo.src.interest.model.GetInterestRes;
import com.example.demo.src.user.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class InterestProvider {

    private final InterestDao interestDao;

    private final UserProvider userProvider;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public InterestProvider(InterestDao interestDao, UserProvider userProvider) {
        this.interestDao = interestDao;
        this.userProvider = userProvider;
    }


    public int checkInterestByUserIdAndPostId(int userId, int postId) throws BaseException {
        try {
            return interestDao.checkInterestByUserIdAndPostId(userId, postId);
        } catch (Exception exception) {
            logger.error("App - checkInterest Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetInterestRes> getInterestsByUserId(int userId) throws BaseException {
        if(userProvider.checkUserId(userId)==0){
            throw new BaseException(GET_USERS_UNKNOWN_USER_ID);
        }
        try {
            List<GetInterestRes> getInterestRes = interestDao.getInterestByUserId(userId);
            return getInterestRes;
        } catch (Exception exception) {
            logger.error("App - getInterestRes Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkInterestId(int interestId) throws BaseException {
        try{
            return interestDao.checkInterestId(interestId);
        }catch (Exception exception){
            logger.error("App - checkInterestId Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
