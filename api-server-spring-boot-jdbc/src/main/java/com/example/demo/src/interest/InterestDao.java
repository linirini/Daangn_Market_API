package com.example.demo.src.interest;

import com.example.demo.src.interest.model.GetInterestRes;
import com.example.demo.src.interest.model.PostInterestReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class InterestDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int checkInterestByUserIdAndPostId(int userId, int postId) {
        String checkInterestQuery = "select exists(select interestId from Interest where userId = ? AND postId = ?)";
        Object[] checkInterestParams = new Object[]{userId, postId};
        return this.jdbcTemplate.queryForObject(checkInterestQuery,int.class, checkInterestParams);
    }

    public int createInterest(PostInterestReq postInterestReq) {
        String createInterestQuery = "insert into Interest (userId, postId, interestStatus) VALUES(?,?,?)";
        Object[] createInterestParams = new Object[]{postInterestReq.getUserId(), postInterestReq.getPostId(), postInterestReq.getInterestStatus()};
        this.jdbcTemplate.update(createInterestQuery, createInterestParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public List<GetInterestRes> getInterestByUserId(int userId) {
        String getInterestByUserIdQuery = "select * from Interest WHERE interestStatus = 'ACTIVE' AND userId = ?";
        int getInterestByUserIdParams = userId;
        return this.jdbcTemplate.query(getInterestByUserIdQuery,
                (rs,rowNum) -> new GetInterestRes(
                        rs.getInt("interestId"),
                        rs.getInt("userId"),
                        rs.getInt("postId"),
                        rs.getString("interestStatus")),
        getInterestByUserIdParams);
    }

    public int deleteInterest(int interestId) {
        String deleteInterestQuery = "update Interest set interestStatus = ?, updatedAt = current_timestamp where interestId = ?";
        Object[] deleteInterestParams = new Object[]{"DELETED", interestId};
        return this.jdbcTemplate.update(deleteInterestQuery,deleteInterestParams);
    }

    public int checkInterestId(int interestId) {
        String checkInterestIdQuery = "select exists(select interestId from Interest where interestId = ?)";
        int checkInterestIdParam = interestId;
        return this.jdbcTemplate.queryForObject(checkInterestIdQuery,
                int.class,
                checkInterestIdParam);
    }
}
