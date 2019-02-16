package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.OrderEntity;
import yummy.entity.UserEntity;

import javax.persistence.Table;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 12:53 2019/2/9
 */
@Repository
@Transactional
@Table(name = "restaurant_order")
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    List<OrderEntity> findByRestaurant(UserEntity userEntity);

    List<OrderEntity> findByMemberAndStatus(UserEntity userEntity,String status);

    List<OrderEntity> findByMember(UserEntity userEntity);
}
