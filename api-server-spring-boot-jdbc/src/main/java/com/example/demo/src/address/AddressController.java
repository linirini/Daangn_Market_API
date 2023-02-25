package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AddressProvider addressProvider;

    @Autowired
    private final AddressService addressService;


    public AddressController(AddressProvider addressProvider, AddressService addressService) {
        this.addressProvider = addressProvider;
        this.addressService = addressService;
    }

    /**
     * 내 동네 추가 및 인증 API
     * [POST] /addresses
     *
     * @return BaseResponse<PostaddressRes>
     */
    @PostMapping("")
    public BaseResponse<PostAddressRes> createAddress(@RequestBody PostAddressReq postAddressReq){
        if(postAddressReq.getUserId()==null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_USER_ID);
        }
        if(postAddressReq.getAddress()==null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
        }
        if(postAddressReq.getLatitude()==null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_LATITUDE);
        }
        if(postAddressReq.getLongitude()==null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_LONGITUDE);
        }
        try{
            PostAddressRes postAddressRes = addressService.createAddress(postAddressReq);
            return new BaseResponse<>(postAddressRes);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 내 동네 설정 API
     * [PATCH] /addresses/:address-id?user-id=
     *
     * @return BaseResponse<String>
     */
    @PatchMapping("/{address-id}")
    public BaseResponse<String>patchAddress(@PathVariable("address-id")Integer addressId, @RequestParam("user-id")Integer userId) throws BaseException {
        if(addressId==null){
            throw new BaseException(PATCH_ADDRESS_EMPTY_ADDRESS_ID);
        }
        if(userId==null){
            throw new BaseException(PATCH_ADDRESS_EMPTY_USER_ID);
        }
        try{
            addressService.patchAddress(addressId, userId);
            String result = "";
            return new BaseResponse<>(result);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 내 동네 인증 횟수 조회 API
     * [GET] /addresses/:address-id
     *
     * @return BaseResponse<GetAddressRes>
     */
    @GetMapping("/{address-id}")
    public BaseResponse<GetAddressRes> getAddress(@PathVariable("address-id")Integer addressId) throws BaseException {
        if(addressId==null){
            throw new BaseException(GET_ADDRESS_EMPTY_ADDRESS_ID);
        }
        try{
            GetAddressRes getAddressRes = addressProvider.getAddress(addressId);
            return new BaseResponse<>(getAddressRes);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 내 동네 인증 횟수 조회 API
     * [PATCH] /addresses/status/:address-id
     *
     * @return BaseResponse<GetAddressRes>
     */
    @PatchMapping("/status/{address-id}")
    public BaseResponse<String> deleteAddress(@PathVariable("address-id")Integer addressId) throws BaseException{
        if(addressId==null){
            throw new BaseException(PATCH_ADDRESS_EMPTY_ADDRESS_ID);
        }
        try{
            addressService.deleteAddress(addressId);
            String result = "";
            return new BaseResponse<>(result);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
