package com.example.demo.src.post;

import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost(PostPostReq postPostReq) {
        String createPostQuery = "insert into Post (userId, title, price, priceProposal,sharing,content,dealAddress) VALUES (?,?,?,?,?,?,?)";
        Object[] createPostParams = new Object[]{postPostReq.getUserId(),postPostReq.getTitle(),postPostReq.getPrice(),postPostReq.getPriceProposal(),postPostReq.getSharing(),postPostReq.getContent(),postPostReq.getDealAddress()};
        this.jdbcTemplate.update(createPostQuery, createPostParams);
        String lastInsertIdQuery = "select last_insert_id()";
        int postId = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
        String createPostImgQuery = "insert into PostImageUrl (postId, imageUrl) VALUES (?,?)";
        for(int i=0;i<postPostReq.getPostImgUrls().size();i++) {
            Object[] createPostImgParams = new Object[]{postId, postPostReq.getPostImgUrls().get(i)};
            this.jdbcTemplate.update(createPostImgQuery,createPostImgParams);
            if(i==0){
                String updatePostImgQuery = "update PostImageUrl set imageUrlStatus = ? where postId = ?";
                Object[] updatePostImgParams = new Object[]{"ACTIVE",postId};
                this.jdbcTemplate.update(updatePostImgQuery,updatePostImgParams);
            }
        }
        return postId;
    }

    public List<GetAllPostRes> getPostsByAddress(String address) {
        String getPostsByAddressQuery = "select Post.*, PostImageUrl.imageUrl from Post join PostImageUrl WHERE Post.status = 'ACTIVE' AND Post.postId = PostImageUrl.postId AND PostImageUrl.imageUrlStatus = 'ACTIVE' AND dealAddress = ?";
        String getPostsByAddressParams = address;
        return this.jdbcTemplate.query(getPostsByAddressQuery,
                (rs,rowNum)->new GetAllPostRes(
                        rs.getInt("postId"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("priceProposal"),
                        rs.getString("salesStatus"),
                        rs.getString("sharing"),
                        rs.getString("content"),
                        rs.getString("pullUp"),
                        rs.getString("dealAddress"),
                        rs.getString("imageUrl")),
                getPostsByAddressParams);
    }

    public GetPostRes getPost(int postId) {
        String getPostQuery = "select * from Post where postId = ? where status = ?";
        List<GetPostImageUrlRes> tmp = getAllPostImageUrl(postId);
        List<String> postImgUrls = new ArrayList<>();
        tmp.forEach(i ->postImgUrls.add(i.getImageUrl()));
        Object[] getPostParams = new Object[]{postId,"ACTIVE"};
        return this.jdbcTemplate.queryForObject(getPostQuery,
                (rs,rowNum)->new GetPostRes(
                        rs.getInt("postId"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("priceProposal"),
                        rs.getString("salesStatus"),
                        rs.getString("sharing"),
                        rs.getString("content"),
                        rs.getString("pullUp"),
                        rs.getString("dealAddress")),
                getPostParams);
    }

    private List<GetPostImageUrlRes> getAllPostImageUrl(int postId){
        String getAllPostImageUrlQuery = "select imageUrl from PostImageUrl where postId = ?";
        int getAllPostImageUrlParam = postId;
        return this.jdbcTemplate.query(getAllPostImageUrlQuery,
                (rs,rowNum)->new GetPostImageUrlRes(
                        rs.getString("imageUrl")),
                getAllPostImageUrlParam);
    }

    public int modifyPost(int postId) {
        String modifyPostQuery = "update Post set pullUp = ?, updatedAt = current_timestamp where postId = ?";
        Object[] modifyPostParams = new Object[]{"ACTIVE",postId};
        return this.jdbcTemplate.update(modifyPostQuery,modifyPostParams);
    }

    public int deletePost(int postId) {
        String deletePostQuery = "update Post set status = ?, updatedAt = current_timestamp where postId = ? ";
        Object[] deletePostParams = new Object[]{"DELETED", postId};
        return this.jdbcTemplate.update(deletePostQuery,deletePostParams);
    }

    public int checkPostId(Integer postId) {
        String checkPostIdQuery = "select exists(select postId from Post where postId = ?)";
        int checkPostIdParam = postId;
        return this.jdbcTemplate.queryForObject(checkPostIdQuery,
                int.class,
                checkPostIdParam);
    }
}
