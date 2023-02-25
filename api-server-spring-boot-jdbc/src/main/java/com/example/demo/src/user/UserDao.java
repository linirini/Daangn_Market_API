package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsersByPhoneNumber(String phoneNumber) {
        String getUsersByPhoneNumberQuery = "select * from User where accountStatus = 'ACTIVE' AND phoneNumber =?";
        String getUsersByPhoneNumberParams = phoneNumber;
        return this.jdbcTemplate.query(getUsersByPhoneNumberQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("nickName"),
                        rs.getString("phoneNumber"),
                        rs.getString("emailAddress"),
                        rs.getDouble("mannerTemp"),
                        rs.getDouble("retransactionHopeRate"),
                        rs.getString("profilePhoto"),
                        rs.getString("accountStatus")),
                getUsersByPhoneNumberParams);
    }

    public GetUserRes getUser(int userId){
        String getUserQuery = "select * from User where userId = ? and accountStatus = ?";
        Object[] getUserParams = new Object[]{userId, "ACTIVE"};
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("nickName"),
                        rs.getString("phoneNumber"),
                        rs.getString("emailAddress"),
                        rs.getDouble("mannerTemp"),
                        rs.getDouble("retransactionHopeRate"),
                        rs.getString("profilePhoto"),
                        rs.getString("accountStatus")),
                getUserParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (nickName, phoneNumber) VALUES (?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getNickName(), postUserReq.getPhoneNumber()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkPhoneNumber(String phoneNumber){
        String checkPhoneNumberQuery = "select exists(select phoneNumber from User where phoneNumber = ?)";
        String checkPhoneNumberParams = phoneNumber;
        return this.jdbcTemplate.queryForObject(checkPhoneNumberQuery,
                int.class,
                checkPhoneNumberParams);

    }

    public int patchUser(PatchUserReq patchUserReq){
        String modifyUserQuery = "update User set nickName = ?, profilePhoto = ?, updatedAt = current_timestamp where userId = ? ";
        Object[] modifyUserParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getProfilePhoto(), patchUserReq.getUserId()};
        return this.jdbcTemplate.update(modifyUserQuery,modifyUserParams);
    }

    public int checkUserId(int userId) {
        String checkUserIdQuery = "select exists(select userId from User where userId = ?)";
        int checkUserIdParam = userId;
        return this.jdbcTemplate.queryForObject(checkUserIdQuery,
                int.class,
                checkUserIdParam);
    }

    public int checkNickName(String nickName){
        String checkNickNameQuery = "select exists(select nickName from User where nickName = ?)";
        String checkNickNameParams = nickName;
        return this.jdbcTemplate.queryForObject(checkNickNameQuery,
                int.class,
                checkNickNameParams);
    }

    public int deleteUser(int userId){
        String deleteUserQuery = "update User set accountStatus = ?, updatedAt = current_timestamp where userId = ? ";
        Object[] deleteUserParams = new Object[]{"DELETED", userId};
        return this.jdbcTemplate.update(deleteUserQuery,deleteUserParams);
    }
    
/*


    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from UserInfo where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }



    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, password,email,userName,ID from UserInfo where ID = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("ID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email")
                ),
                getPwdParams
                );

    }
    */
}
