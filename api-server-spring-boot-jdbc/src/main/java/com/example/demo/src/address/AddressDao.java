package com.example.demo.src.address;

import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PostAddressReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class AddressDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public int createAddress(PostAddressReq postAddressReq) {
        String createAddressQuery = "insert into Address (userId, address, latitude, longitude) VALUES (?,?,?,?)";
        Object[] createAddressParams = new Object[]{postAddressReq.getUserId(), postAddressReq.getAddress(), postAddressReq.getLatitude(), postAddressReq.getLongitude()};
        this.jdbcTemplate.update(createAddressQuery, createAddressParams);
        String lastInsertIdQuery = "select last_insert_id()";
        int addressId = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        String updateConfirmNumQuery = "update Address set confirmStatus = 1 where addressId = ?";
        int updateConfirmNumParam = addressId;
        this.jdbcTemplate.update(updateConfirmNumQuery, updateConfirmNumParam);
        String updateAddressStatusQuery = "update Address set addressStatus = \'DELETED\' where userId = ? AND addressId != ?";
        Object[] updateAddressStatusParams = new Object[]{postAddressReq.getUserId(), addressId};
        this.jdbcTemplate.update(updateAddressStatusQuery, updateAddressStatusParams);
        return addressId;
    }

    @Transactional(readOnly = true)
    public int checkAddressId(int addressId) {
        String checkAddressIdQuery = "select exists(select addressId from Address where addressId = ? and status = ?)";
        Object[] checkAddressIdParam = new Object[]{addressId,"ACTIVE"};
        return this.jdbcTemplate.queryForObject(checkAddressIdQuery,
                int.class,
                checkAddressIdParam);
    }

    @Transactional
    public int patchAddress(int addressId, int userId) {
        String modifyOriginAddressQuery = "update Address set addressStatus = ? where userId = ? and addressStatus = ? and status = ?";
        Object[] modifyOriginAddressParams = new Object[]{"DELETED", userId, "ACTIVE", "ACTIVE"};
        this.jdbcTemplate.update(modifyOriginAddressQuery, modifyOriginAddressParams);
        String modifyAddressQuery = "update Address set addressStatus = ? where addressId = ?";
        Object[] modifyAddressParams = new Object[]{"ACTIVE",addressId};
        return this.jdbcTemplate.update(modifyAddressQuery,modifyAddressParams);
    }

    @Transactional(readOnly = true)
    public GetAddressRes getAddress(int addressId) {
        String getAddressQuery = "select * from Address where addressId = ? and status = ?";
        Object[] getAddressParam = new Object[]{addressId,"ACTIVE"};
        return this.jdbcTemplate.queryForObject(getAddressQuery,
                (rs, rowNum) -> new GetAddressRes(
                        rs.getInt("addressId"),
                        rs.getInt("userId"),
                        rs.getString("address"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("confirmStatus"),
                        rs.getString("addressStatus")),
                getAddressParam);
    }

    @Transactional
    public int deleteAddress(int addressId) {
        String deleteAddressQuery = "update Address set status = ? where addressId = ?";
        Object[] deleteAddressParams = new Object[]{"DELETED",addressId};
        return this.jdbcTemplate.update(deleteAddressQuery,deleteAddressParams);
    }

    @Transactional
    public void patchDeleteAddress(int addressId, int userId) {
        String modifyOriginAddressQuery = "update Address set addressStatus = ? where userId = ? and addressStatus = ? and status = ?";
        Object[] modifyOriginAddressParams = new Object[]{"ACTIVE", userId, "DELETED", "ACTIVE"};
        this.jdbcTemplate.update(modifyOriginAddressQuery, modifyOriginAddressParams);
        String modifyAddressQuery = "update Address set addressStatus = ? where addressId = ?";
        Object[] modifyAddressParams = new Object[]{"DELETED",addressId};
        this.jdbcTemplate.update(modifyAddressQuery,modifyAddressParams);
    }
}
