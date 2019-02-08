package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.RestaurantMessageEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 19:20 2019/2/3
 */
@Repository
@Transactional
@Table(name = "restaurant_message")
public interface RestaurantMessageRepository extends JpaRepository<RestaurantMessageEntity,Integer> {
}
