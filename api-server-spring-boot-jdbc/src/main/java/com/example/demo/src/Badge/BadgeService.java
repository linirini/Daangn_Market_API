package com.example.demo.src.Badge;

import com.example.demo.config.BaseException;
import com.example.demo.src.Badge.model.PostBadgeReq;
import com.example.demo.src.Badge.model.PostBadgeRes;
import com.example.demo.src.user.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BadgeService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BadgeDao badgeDao;

    private final BadgeProvider badgeProvider;

    private final UserProvider userProvider;

    @Autowired
    public BadgeService(BadgeDao badgeDao, BadgeProvider badgeProvider, UserProvider userProvider) {
        this.badgeDao = badgeDao;
        this.badgeProvider = badgeProvider;
        this.userProvider = userProvider;
    }


    public PostBadgeRes createBadge(PostBadgeReq postBadgeReq) throws BaseException {
        if(badgeProvider.checkBadgeByUserIdAndBadgeTabId(postBadgeReq.getUserId(),postBadgeReq.getBadgeTagId())==1){
            throw new BaseException(POST_BADGE_ALREADY_EXIST);
        }
        try{
            int badgeId = badgeDao.createBadge(postBadgeReq);
            return new PostBadgeRes(badgeId);
        }catch (Exception exception) {
            logger.error("App - createBadge Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void patchBadge(int badgeId, int userId) throws BaseException{
        if(badgeProvider.checkBadgeId(badgeId)==0){
            throw new BaseException(PATCH_BADGE_UNKNOWN_BADGE_ID);
        }
        if(userProvider.checkUserId(userId)==0){
            throw new BaseException(GET_USERS_UNKNOWN_USER_ID);
        }
        try{
            int result = badgeDao.patchBadge(badgeId, userId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_BADGE);
            }
        }catch (Exception exception){
            logger.error("App - patchBadge Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
