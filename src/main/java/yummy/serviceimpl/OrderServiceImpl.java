package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.OrderRepository;
import yummy.entity.OrderEntity;
import yummy.entity.UserEntity;
import yummy.service.OrderService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:50 2019/2/12
 */
@Service
public class OrderServiceImpl extends PublicServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.repository = orderRepository;
    }

    @Override
    public List<OrderEntity> findByMemberAndStatus(UserEntity userEntity, String status) {
        return orderRepository.findByMemberAndStatus(userEntity,status);
    }
}
