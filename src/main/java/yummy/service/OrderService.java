package yummy.service;

import yummy.entity.OrderEntity;
import yummy.entity.UserEntity;

import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 7:36 2019/2/12
 */
public interface OrderService extends PublicService{

    List<OrderEntity> findByMemberAndStatus(UserEntity userEntity, String status);
}
