package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.src.user.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AddressService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AddressDao addressDao;

    private final AddressProvider addressProvider;

    private final UserProvider userProvider;

    @Autowired
    public AddressService(AddressDao addressDao, AddressProvider addressProvider, UserProvider userProvider) {
        this.addressDao = addressDao;
        this.addressProvider = addressProvider;
        this.userProvider = userProvider;
    }

    public PostAddressRes createAddress(PostAddressReq postAddressReq) throws BaseException{
        if(userProvider.checkUserId(postAddressReq.getUserId())==0){
            throw new BaseException(GET_USERS_UNKNOWN_USER_ID);
        }
        try{
            int addressId = addressDao.createAddress(postAddressReq);
            return new PostAddressRes(addressId);
        }catch (Exception exception) {
            logger.error("App - createAddress Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void patchAddress(int addressId, int userId) throws BaseException {
        if(userProvider.checkUserId(userId)==0){
            throw new BaseException(GET_USERS_UNKNOWN_USER_ID);
        }
        if(addressProvider.checkAddressId(addressId)==0){
            throw new BaseException(GET_ADDRESS_UNKNOWN_ADDRESS_ID);
        }
        try{
            int result = addressDao.patchAddress(addressId, userId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_ADDRESS);
            }
        }catch (Exception exception) {
            logger.error("App - patchAddress Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteAddress(int addressId) throws BaseException{
        try{
            if(addressProvider.checkAddressId(addressId)==0){
                throw new BaseException(GET_ADDRESS_UNKNOWN_ADDRESS_ID);
            }
            if(addressProvider.getAddress(addressId).getAddressStatus().equals("ACTIVE")){
                addressDao.patchDeleteAddress(addressId,addressProvider.getAddress(addressId).getUserId());
            }
            int result = addressDao.deleteAddress(addressId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_ADDRESS);
            }
        }catch (Exception exception) {
            logger.error("App - deleteAddress Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
