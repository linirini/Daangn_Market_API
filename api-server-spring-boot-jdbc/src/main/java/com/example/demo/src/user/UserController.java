package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.KakaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;
import static com.example.demo.utils.ValidationRegex.isRegexPhoneNumber;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final KakaoService kakaoService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, KakaoService kakaoService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.kakaoService = kakaoService;
    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:user-id
     *
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/{user-id}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("user-id") int userId) {
        try {
            GetUserRes getUserRes = userProvider.getUser(userId);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원가입 API
     * [POST] /users
     *
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) throws BaseException {
        if (postUserReq.getPhoneNumber() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUMBER);
        }
        if (postUserReq.getNickName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NICK_NAME);
        }
        if (!isRegexPhoneNumber(postUserReq.getPhoneNumber())) {
            return new BaseResponse<>(POST_USERS_INVALID_PHONENUMBER);
        }
        if (!isRegexEmail(postUserReq.getEmailAddress())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL_ADDRESS);
        }
        if (postUserReq.getEmailAddress() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL_ADDRESS);
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인 API
     * [POST] /users/login
     *
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        try {
            if (postLoginReq.getNickName() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_NICK_NAME);
            }
            if (postLoginReq.getPhoneNumber() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_PHONENUMBER);
            }
            if (!isRegexPhoneNumber(postLoginReq.getPhoneNumber())) {
                return new BaseResponse<>(POST_USERS_INVALID_PHONENUMBER);
            }
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 소셜 로그인 토큰 받기 API
     * [POST] /oauth
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @GetMapping("/oauth")
    public BaseResponse<String> socialLogInRedirected(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessToken(code);
        return new BaseResponse<>(accessToken);
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:user-id
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{user-id}")
    public BaseResponse<String> modifyUser(@PathVariable("user-id") int userId, @RequestBody User user) {
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 같은지 확인
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (user.getNickName() == null) {
                throw new BaseException(PATCH_USERS_EMPTY_NICK_NAME);
            }
            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getNickName(), user.getProfilePhoto());
            userService.modifyUser(patchUserReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 삭제 API
     * [DELETE] /users/:user-id
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("status/{user-id}")
    public BaseResponse<String> deleteUser(@PathVariable("user-id") int userId) {
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.deleteUser(userId);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
