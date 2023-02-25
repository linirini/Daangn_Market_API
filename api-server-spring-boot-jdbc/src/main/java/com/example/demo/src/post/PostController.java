package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;

    public PostController(PostProvider postProvider, PostService postService) {
        this.postProvider = postProvider;
        this.postService = postService;
    }

    /**
     * 게시물 생성 API
     * [POST] /posts
     *
     * @return BaseResponse<PostPostRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostRes> createPost(@RequestBody PostPostReq postPostReq){
        if(postPostReq.getTitle()==null){
            return new BaseResponse<>(POST_POST_EMPTY_TITLE);
        }
        if(postPostReq.getContent()==null){
            return new BaseResponse<>(POST_POST_EMPTY_CONTENT);
        }
        if(postPostReq.getPrice()==null&&postPostReq.getSharing()==null){
            return new BaseResponse<>(POST_POST_EMPTY_PRICE);
        }
        if(postPostReq.getPostImgUrls().size()==0){
            return new BaseResponse<>(POST_POST_EMPTY_IMG_URLS);
        }
        try{
            if(postPostReq.getSharing()==null){
                postPostReq.setSharing("DELETED");
            }
            if(postPostReq.getSharing().equals("ACTIVE")){
                postPostReq.setPrice(0);
            }
            if(postPostReq.getPriceProposal()==null){
                postPostReq.setPriceProposal("DELETED");
            }
            PostPostRes postPostRes = postService.createPost(postPostReq);
            return new BaseResponse<>(postPostRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 전체 게시물 조회 API
     * 지역으로 조회 API
     * [GET] /posts? address=
     *
     * @return BaseResponse<List <GetPostRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetAllPostRes>>getPosts(@RequestParam(value = "address")String address){
        try{
            if(address == null){
                throw new BaseException(GET_POST_EMPTY_ADDRESS);
            }
            List<GetAllPostRes> getAllPostRes = postProvider.getPostsByAddress(address);
            return new BaseResponse<>(getAllPostRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 게시물 1개 조회 API
     * [GET] /posts/:post-id
     *
     * @return BaseResponse<GetPostRes>
     */
    @ResponseBody
    @GetMapping("/{post-id}")
    public BaseResponse<GetPostRes> getPost(@PathVariable("post-id") int postId) {
        try {
            GetPostRes getPostRes = postProvider.getPost(postId);
            return new BaseResponse<>(getPostRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 게시물정보변경 API
     * [PATCH] /posts/:post-id
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/pullUp/{post-id}")
    public BaseResponse<String> modifyUser(@PathVariable("post-id") int postId) {
        try {
            postService.modifyPost(postId);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 게시물 삭제 API
     * [DELETE] /posts/:post-id
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/status/{post-id}")
    public BaseResponse<String> deleteUser(@PathVariable("post-id") int postId){
        try {
            //본인 확인 생략!
            postService.deletePost(postId);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}


