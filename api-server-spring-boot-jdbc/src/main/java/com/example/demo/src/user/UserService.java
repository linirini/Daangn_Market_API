package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkPhoneNumber(postUserReq.getPhoneNumber()) == 1){
            throw new BaseException(POST_USERS_EXISTS_PHONENUMBER);
        }
        if(userProvider.checkNickName(postUserReq.getNickName()) == 1){
            throw new BaseException(PATCH_USERS_EXISTS_NICK_NAME);
        }

        String phoneNumber;
        try{
            phoneNumber = new AES128(Secret.USER_INFO_PHONENUMBER_KEY).encrypt(postUserReq.getPhoneNumber());
            postUserReq.setPhoneNumber(phoneNumber);
        }catch(Exception ignored){
            throw new BaseException(PHONENUMBER_ENCRYPTION_ERROR);
        }
        try{
            int userId = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userId);
            return new PostUserRes(jwt,userId);
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUser(PatchUserReq patchUserReq) throws BaseException {
        try{
            if(userProvider.checkUserId(patchUserReq.getUserId()) == 0){
                throw new BaseException(DELETE_USERS_UNKNOWN_USER_ID);
            }
            if(userProvider.checkNickName(patchUserReq.getNickName()) == 1){
                throw new BaseException(PATCH_USERS_EXISTS_NICK_NAME);
            }
            int result = userDao.patchUser(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USER);
            }
        } catch(Exception exception){
            logger.error("App - modifyUserName Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteUser(int userId) throws BaseException {
        try{
            if(userProvider.checkUserId(userId) == 0){
                throw new BaseException(DELETE_USERS_UNKNOWN_USER_ID);
            }
            if(userProvider.getUser(userId).getAccountStatus().equals("DELETED")){
                throw new BaseException(DELETE_USERS_ALREADY_DELETED);
            }
            int result = userDao.deleteUser(userId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USER);
            }
        } catch(Exception exception){
            logger.error("App - modifyUserName Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
