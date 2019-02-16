package yummy.service;

import yummy.entity.OrderEntity;
import yummy.entity.UserEntity;

import java.text.ParseException;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 7:36 2019/2/12
 */
public interface OrderService extends PublicService{

    List<OrderEntity> findByMemberAndStatus(UserEntity userEntity, String status);

    Double calculateCancelPrice(OrderEntity orderEntity);

    List<OrderEntity> filterByStatusAndDateAndPrice(List<OrderEntity> orderEntities, String status, String orderDate, String price) throws ParseException;

    List<OrderEntity> findByMember(UserEntity userEntity);

    List<OrderEntity> filterByRestaurantType(List<OrderEntity> orderEntities,String restaurantType);
}
