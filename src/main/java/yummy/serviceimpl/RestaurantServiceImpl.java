package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.ApplyRepository;
import yummy.dao.RestaurantMessageRepository;
import yummy.entity.ApplyEntity;
import yummy.service.RestaurantService;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:30 2019/1/18
 */
@Service
public class RestaurantServiceImpl extends PublicServiceImpl implements RestaurantService {
    private final RestaurantMessageRepository restaurantMessageRepository;

    private final ApplyRepository applyRepository;

    @Autowired
    public RestaurantServiceImpl(ApplyRepository applyRepository, RestaurantMessageRepository restaurantMessageRepository) {
        this.applyRepository = applyRepository;
        this.restaurantMessageRepository = restaurantMessageRepository;
    }

    @Override
    public boolean addApply(ApplyEntity applyEntity) {
        try {
            applyRepository.saveAndFlush(applyEntity);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
