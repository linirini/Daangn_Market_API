package com.example.demo.src.Badge;

import com.example.demo.config.BaseException;
import com.example.demo.src.Badge.model.GetBadgeRes;
import com.example.demo.src.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BadgeProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BadgeDao badgeDao;

    private final UserDao userDao;

    @Autowired
    public BadgeProvider(BadgeDao badgeDao, UserDao userDao) {
        this.badgeDao = badgeDao;
        this.userDao = userDao;
    }

    public int checkBadgeByUserIdAndBadgeTabId(Integer userId, Integer badgeTagId) throws BaseException {
        try{
            return badgeDao.checkBadgeByUserIdAndBadgeTabId(userId, badgeTagId);
        }catch (Exception exception){
            logger.error("App - checkBadge Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBadgeRes getBadge(int badgeId) throws BaseException{
        if(badgeDao.checkBadgeId(badgeId)==0){
            throw new BaseException(GET_BADGE_UNKNOWN_BADGE_ID);
        }
        try{
            GetBadgeRes getBadgeRes = badgeDao.getBadge(badgeId);
            return getBadgeRes;
        }catch (Exception exception) {
            logger.error("App - getBadgeProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBadgeRes> getAllBadgeByUserId(int userId)throws BaseException {
        if(userDao.getUser(userId)==null){
            throw new BaseException(GET_USERS_UNKNOWN_USER_ID);
        }
        try{
            List<GetBadgeRes> getBadgeResList = badgeDao.getAllBadge(userId);
            return getBadgeResList;
        }catch (Exception exception) {
            logger.error("App - getAllBadgeProvider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkBadgeId(int badgeId) throws BaseException {
        try{
            return badgeDao.checkBadgeId(badgeId);
        }catch (Exception exception){
            logger.error("App - checkBadgeId Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
