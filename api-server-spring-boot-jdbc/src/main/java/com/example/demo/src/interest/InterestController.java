package com.example.demo.src.interest;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.interest.model.GetInterestRes;
import com.example.demo.src.interest.model.PostInterestReq;
import com.example.demo.src.interest.model.PostInterestRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/interests")
public class InterestController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final InterestProvider interestProvider;
    @Autowired
    private final InterestService interestService;
    @Autowired
    private final JwtService jwtService;

    public InterestController(InterestProvider interestProvider, InterestService interestService, JwtService jwtService) {
        this.interestProvider = interestProvider;
        this.interestService = interestService;
        this.jwtService = jwtService;
    }

    /**
     * 관심 글 추가 API
     * [POST] /interests
     *
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostInterestRes> createInterest(@RequestBody PostInterestReq postInterestReq) {
        if (postInterestReq.getPostId() == null) {
            return new BaseResponse<>(POST_INTEREST_EMPTY_POST_ID);
        }
        if (postInterestReq.getUserId() == null) {
            return new BaseResponse<>(POST_INTEREST_EMPTY_USER_ID);
        }
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if (postInterestReq.getUserId() != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostInterestRes postInterestRes = interestService.createInterest(postInterestReq);
            return new BaseResponse<>(postInterestRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 관심글 조회 API
     * [GET] /interests/user-id=
     *
     * @return BaseResponse<List < GetInterestRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetInterestRes>> getInterests(@RequestParam(value = "user-id") Integer userId) {
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetInterestRes> getInterestRes = interestProvider.getInterestsByUserId(userId);
            return new BaseResponse<>(getInterestRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 관심 목록 삭제 API
     * [DELETE] /interests/:interest-id
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/status/{interest-id}")
    public BaseResponse<String> deleteInterest(@PathVariable("interest-id") int interestId) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if (interestProvider.getInterest(interestId).getUserId() != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            interestService.deleteInterest(interestId);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
