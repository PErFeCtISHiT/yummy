package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.ProductEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 12:52 2019/2/9
 */
@Repository
@Transactional
@Table(name = "restaurant_product")
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
}
