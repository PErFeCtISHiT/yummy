package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.OrderRepository;
import yummy.entity.OrderEntity;
import yummy.entity.UserEntity;
import yummy.service.OrderService;
import yummy.util.NamedContext;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Override
    public Double calculateCancelPrice(OrderEntity orderEntity) {
        Time time = new Time(new java.util.Date().getTime());
        Time orderTime = orderEntity.getOrderTime();
        int min = (time.getHours() - orderTime.getHours()) * 60 + time.getMinutes() - orderTime.getMinutes();
        if(min > 30)
            return 0.0;
        else if(min >= 15)
            return (orderEntity.getPrice() * (1 - orderEntity.getDiscount())) * 0.6;
        else if(min >= 0)
            return orderEntity.getPrice() * (1 - orderEntity.getDiscount());
        else
            return -1.0;
    }

    @Override
    public List<OrderEntity> filterByStatusAndDateAndPrice(List<OrderEntity> orderEntities, String status, String orderDate, String price) throws ParseException {
        List<OrderEntity> ret = new ArrayList<>();
        if(!status.equals(NamedContext.ALL)){
            for(OrderEntity orderEntity : orderEntities){
                if(orderEntity.getStatus().equals(status))
                    ret.add(orderEntity);
            }
            orderEntities = ret;
            ret = new ArrayList<>();
        }
        if(!orderDate.equals(NamedContext.ALL)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d1 = sdf.parse(orderDate);
            java.sql.Date d2 = new java.sql.Date(d1.getTime());
            for(OrderEntity orderEntity : orderEntities){
                if(orderEntity.getEndDate().equals(d2))
                    ret.add(orderEntity);
            }
            orderEntities = ret;
            ret = new ArrayList<>();
        }
        if(!price.equals(NamedContext.ALL)){
            Integer orderPrice = Integer.valueOf(price);
            for(OrderEntity orderEntity : orderEntities){
                switch (orderPrice){
                    case 20:
                        if(orderEntity.getPrice() <= 20)
                            ret.add(orderEntity);
                        break;
                    case 40:
                        if(orderEntity.getPrice() > 20 && orderEntity.getPrice() <= 40)
                            ret.add(orderEntity);
                        break;
                        default:
                            if(orderEntity.getPrice() > 40)
                                ret.add(orderEntity);
                            break;
                }
            }
            orderEntities = ret;
        }
        return orderEntities;
    }

    @Override
    public List<OrderEntity> findByMember(UserEntity userEntity) {
        return orderRepository.findByMember(userEntity);
    }

    @Override
    public List<OrderEntity> filterByRestaurantType(List<OrderEntity> orderEntities, String restaurantType) {
        if(restaurantType.equals(NamedContext.ALL))
            return orderEntities;
        else {
            List<OrderEntity> ret = new ArrayList<>();
            for(OrderEntity orderEntity : orderEntities){
                if(orderEntity.getRestaurant().getRestaurantMessageEntity().getRestaurantType().equals(restaurantType))
                    ret.add(orderEntity);
            }
            return ret;
        }
    }
}
