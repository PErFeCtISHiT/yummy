package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.AddressRepository;
import yummy.entity.AddressEntity;
import yummy.service.AddressService;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:06 2019/1/28
 */
@Service
public class AddressServiceImpl extends PublicServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.repository = addressRepository;
        this.addressRepository = addressRepository;
    }


    @Override
    public AddressEntity findAddressByAddressName(String addressName) {
        return addressRepository.findByAddressName(addressName);
    }

}
