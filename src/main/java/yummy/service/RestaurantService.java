package yummy.service;

import yummy.entity.*;

import java.util.List;
import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:29 2019/1/18
 */
public interface RestaurantService extends PublicService{
    boolean addApply(ApplyEntity applyEntity);

    boolean addProduct(ProductEntity productEntity);

    List<ProductEntity> findAllProducts();

    List<RestaurantMessageEntity> findAllRestaurantMessages();

    RestaurantMessageEntity findRestaurantMessageById(Integer id);

    List<OrderEntity> findOrders(UserEntity restaurant);

    ProductEntity findProductById(int anInt);

    boolean modifyProduct(ProductEntity productEntity);

    boolean removeProducts(String pidList);

    boolean addProducts(String pidList);

    String modifyPidList(String pidList);
}
