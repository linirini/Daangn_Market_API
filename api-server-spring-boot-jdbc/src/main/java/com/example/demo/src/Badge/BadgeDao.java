package com.example.demo.src.Badge;

import com.example.demo.src.Badge.model.GetBadgeRes;
import com.example.demo.src.Badge.model.PostBadgeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BadgeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int checkBadgeByUserIdAndBadgeTabId(Integer userId, Integer badgeTagId) {
        String checkBadgeQuery = "select exists(select badgeId from Badge where userId = ? AND badgeTagId = ?)";
        Object[] checkBadgeParams = new Object[]{userId, badgeTagId};
        return this.jdbcTemplate.queryForObject(checkBadgeQuery,int.class, checkBadgeParams);
    }

    public int createBadge(PostBadgeReq postBadgeReq) {
        String createBadgeQuery = "insert into Badge (userId, badgeTagId) VALUES (?,?)";
        Object[] createBadgeParams = new Object[]{postBadgeReq.getUserId(), postBadgeReq.getBadgeTagId()};
        this.jdbcTemplate.update(createBadgeQuery,createBadgeParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public GetBadgeRes getBadge(int badgeId) {
        String getBadgeByBadgeIdQuery = "select * FROM Badge WHERE badgeId = ?";
        int getBadgeByBadgeIdParam = badgeId;
        return this.jdbcTemplate.queryForObject(getBadgeByBadgeIdQuery,
                (rs,rowNum)->new GetBadgeRes(
                        rs.getInt("badgeId"),
                        rs.getInt("userId"),
                        rs.getInt("badgeTagId"),
                        rs.getString("badgeStatus")),
                getBadgeByBadgeIdParam);
    }

    public int patchBadge(int badgeId,int userId) {
        String setOriginRepresentativeBadgeQuery="update Badge set badgeStatus = ? where badgeStatus = ? and userId = ?";
        Object[] setOriginRepresentativeBadgeParams = new Object[]{"DELETED","ACTIVE",userId};
        this.jdbcTemplate.update(setOriginRepresentativeBadgeQuery,setOriginRepresentativeBadgeParams);
        String setRepresentativeBadgeQuery = "update Badge set badgeStatus = ? where badgeId = ?";
        Object[] setRepresentativeBadgeParams = new Object[]{"ACTIVE",badgeId};
        return this.jdbcTemplate.update(setRepresentativeBadgeQuery,setRepresentativeBadgeParams);
    }

    public List<GetBadgeRes> getAllBadge(int userId) {
        String getBadgeByBadgeIdQuery = "select * FROM Badge WHERE userId = ?";
        int getBadgeByBadgeIdParam = userId;
        return this.jdbcTemplate.query(getBadgeByBadgeIdQuery,
                (rs,rowNum)->new GetBadgeRes(
                        rs.getInt("badgeId"),
                        rs.getInt("userId"),
                        rs.getInt("badgeTagId"),
                        rs.getString("badgeStatus")),
                getBadgeByBadgeIdParam);
    }

    public int checkBadgeId(int badgeId) {
        String checkBadgeIdQuery = "select exists(select badgeId From Badge where badgeId = ?)";
        int checkBadgeIdParam = badgeId;
        return this.jdbcTemplate.queryForObject(checkBadgeIdQuery,
                int.class,
                checkBadgeIdParam);
    }
}
