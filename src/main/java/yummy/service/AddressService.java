package yummy.service;

import yummy.entity.AddressEntity;
import yummy.entity.MemberMessageEntity;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:06 2019/1/28
 */
public interface AddressService extends PublicService{


    AddressEntity findAddressByAddressName(String addressName);

}
