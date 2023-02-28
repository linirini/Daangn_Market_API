package com.example.demo.src.Badge;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.Badge.model.GetBadgeRes;
import com.example.demo.src.Badge.model.PostBadgeReq;
import com.example.demo.src.Badge.model.PostBadgeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/badges")
public class BadgeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BadgeProvider badgeProvider;
    @Autowired
    private final BadgeService badgeService;
    @Autowired
    private final JwtService jwtService;

    public BadgeController(BadgeProvider badgeProvider, BadgeService badgeService, JwtService jwtService) {
        this.badgeProvider = badgeProvider;
        this.badgeService = badgeService;
        this.jwtService = jwtService;
    }

    /**
     * 획득한 배지 추가 API
     * [POST] /badges
     *
     * @return BaseResponse<PostBadgeRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostBadgeRes> createBadge(@RequestBody PostBadgeReq postBadgeReq) {


        if (postBadgeReq.getBadgeTagId() == null) {
            return new BaseResponse<>(POST_BADGES_EMPTY_BADGE_TAG_ID);
        }
        if (postBadgeReq.getUserId()==null) {
            return new BaseResponse<>(POST_BADGES_EMPTY_USER_ID);
        }
        try {
            PostBadgeRes postBadgeRes = badgeService.createBadge(postBadgeReq);
            return new BaseResponse<>(postBadgeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 대표 뱃지 변경 API
     * [PATCH] /badges/representative/:badge-id?user-id=
     *
     * @return BaseResponse<String>
     */
    // Body
    @ResponseBody
    @PatchMapping("/representative/{badge-id}")
    public BaseResponse<String> patchBadge(@RequestParam("user-id")Integer userId,@PathVariable("badge-id") Integer badgeId) {
        if (badgeId==null) {
            return new BaseResponse<>(PATCH_BADGES_EMPTY_BADGE_ID);
        }
        if (userId==null) {
            return new BaseResponse<>(PATCH_BADGES_EMPTY_USER_ID);
        }
        try {
            //jwt에서 idx 추출.
            int userIdByJwt = jwtService.getUserId();
            //userIdx와 접근한 유저가 같은지 확인
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            badgeService.patchBadge(badgeId,userId);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 특정 뱃지 조회 API
     * [PATCH] /badges/:badge-id
     *
     * @return BaseResponse<GetBadgeRes>
     */
    // Body
    @ResponseBody
    @GetMapping("/{badge-id}")
    public BaseResponse<GetBadgeRes> patchBadge(@PathVariable("badge-id") Integer badgeId) {
        if (badgeId==null) {
            return new BaseResponse<>(GET_BADGES_EMPTY_BADGE_ID);
        }
        try {
            GetBadgeRes getBadgeRes= badgeProvider.getBadge(badgeId);
            return new BaseResponse<>(getBadgeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 획득한 뱃지 전체 조회 API
     * [PATCH] /badges?user-id
     *
     * @return BaseResponse<List<GetBadgeRes>>
     */
    // Body
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetBadgeRes>> getAllBadge(@RequestParam("user-id") Integer userId) {
        if (userId==null) {
            return new BaseResponse<>(GET_BADGES_EMPTY_USER_ID);
        }
        try {
            List<GetBadgeRes> getBadgeRes= badgeProvider.getAllBadgeByUserId(userId);
            return new BaseResponse<>(getBadgeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
