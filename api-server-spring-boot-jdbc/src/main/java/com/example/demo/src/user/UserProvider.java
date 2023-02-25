package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    //private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        //this.jwtService = jwtService;
    }

    public GetUserRes getUser(int userId) throws BaseException {
        if(userDao.checkUserId(userId) == 0){
            throw new BaseException(GET_USERS_UNKNOWN_USER_ID);
        }
        try {
            GetUserRes getUserRes = userDao.getUser(userId);
            return getUserRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserId(int userId) throws BaseException{
        try{
            return userDao.checkUserId(userId);
        }catch (Exception exception){
            logger.error("App - checkUserId Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkPhoneNumber(String phoneNumber) throws BaseException{
        try{
            return userDao.checkPhoneNumber(phoneNumber);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        try {
            //User user = userDao.getPwd(postLoginReq);

            //String encryptPwd;
            /*try {
                encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
            } catch (Exception exception) {
                logger.error("App - logIn Provider Encrypt Error", exception);
                throw new BaseException(PASSWORD_DECRYPTION_ERROR);
            }*/
            List<GetUserRes> userResList = userDao.getUsersByPhoneNumber(postLoginReq.getPhoneNumber());
            for(GetUserRes userRes : userResList) {
                if (userRes.getPhoneNumber().equals(postLoginReq.getPhoneNumber()) && userRes.getAccountStatus().equals("ACTIVE")) {
                    int userId = userRes.getUserId();
                    //String jwt = jwtService.createJwt(userId);
                    return new PostLoginRes(userId);
                }
            }
            throw new BaseException(FAILED_TO_LOGIN);
        } catch (Exception exception) {
            logger.error("App - logIn Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNickName(String nickName) throws BaseException{
        try{
            return userDao.checkNickName(nickName);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*
    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
    */
}
