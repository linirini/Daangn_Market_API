package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.GetAddressRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.GET_ADDRESS_UNKNOWN_ADDRESS_ID;

@Service
public class AddressProvider {

    private final AddressDao addressDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AddressProvider(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public GetAddressRes getAddress(int addressId) throws BaseException {
        if(addressDao.checkAddressId(addressId)==0){
            throw new BaseException(GET_ADDRESS_UNKNOWN_ADDRESS_ID);
        }
        try{
            GetAddressRes getAddressRes = addressDao.getAddress(addressId);
            return getAddressRes;
        }catch (Exception exception) {
            logger.error("App - getAddress Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAddressId(int addressId) throws BaseException {
        try{
            return addressDao.checkAddressId(addressId);
        }catch (Exception exception){
            logger.error("App - checkAddressId Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
