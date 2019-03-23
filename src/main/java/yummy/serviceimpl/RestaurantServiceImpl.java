package yummy.serviceimpl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.*;
import yummy.entity.*;
import yummy.service.RestaurantService;
import yummy.util.NamedContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:30 2019/1/18
 */
@Service
public class RestaurantServiceImpl extends PublicServiceImpl implements RestaurantService {
    private final RestaurantMessageRepository restaurantMessageRepository;

    private final ApplyRepository applyRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public RestaurantServiceImpl(UserRepository userRepository, ApplyRepository applyRepository, RestaurantMessageRepository restaurantMessageRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.applyRepository = applyRepository;
        this.restaurantMessageRepository = restaurantMessageRepository;
        this.repository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean addApply(ApplyEntity applyEntity) {
        try {
            applyRepository.saveAndFlush(applyEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addProduct(ProductEntity productEntity) {
        try {
            productRepository.saveAndFlush(productEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ProductEntity> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<RestaurantMessageEntity> findAllRestaurantMessages() {
        return restaurantMessageRepository.findAll();
    }

    @Override
    public RestaurantMessageEntity findRestaurantMessageById(Integer id) {
        return restaurantMessageRepository.findOne(id);
    }

    @Override
    public List<OrderEntity> findOrders(UserEntity restaurant) {
        List<OrderEntity> orderEntities = orderRepository.findByRestaurant(restaurant);
        List<OrderEntity> ret = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus().equals(NamedContext.UNORDERED) && orderEntity.getEndDate().after(new Date(new java.util.Date().getTime()))) {
                orderEntity.setRestaurant(null);
                orderEntity.setMember(null);
                ret.add(orderEntity);
            }
        }
        return ret;
    }

    @Override
    public ProductEntity findProductById(int anInt) {
        return productRepository.findOne(anInt);
    }

    @Override
    public boolean modifyProduct(ProductEntity productEntity) {
        try {
            productRepository.saveAndFlush(productEntity);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean removeProducts(String pidList) {
        JSONArray array = new JSONArray(pidList);
        List<ProductEntity> productEntities = new ArrayList<>();
        for(Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            ProductEntity productEntity = this.findProductById(jsonObject.getInt(NamedContext.PID));
            if(productEntity.getNum() < jsonObject.getInt(NamedContext.NUM)){
                return false;
            }
            productEntity.setNum(productEntity.getNum() - jsonObject.getInt(NamedContext.NUM));
            productEntities.add(productEntity);
        }
        for(ProductEntity productEntity : productEntities){
            if(!this.modifyProduct(productEntity)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addProducts(String pidList) {
        JSONArray array = new JSONArray(pidList);
        for(Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            ProductEntity productEntity = this.findProductById(jsonObject.getInt(NamedContext.PID));
            productEntity.setNum(productEntity.getNum() + jsonObject.getInt(NamedContext.NUM));
            if(!this.modifyProduct(productEntity)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String modifyPidList(String pidList) {
        JSONArray array = new JSONArray(pidList);
        JSONArray jsonArray = new JSONArray();
        for(Object o : array) {
            JSONObject jsonObject = (JSONObject) o;
            ProductEntity productEntity = this.findProductById(jsonObject.getInt(NamedContext.PID));
            jsonObject.put(NamedContext.PRODUCTNAME,productEntity.getProductName());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @Override
    public boolean saveRestaurantMessage(RestaurantMessageEntity restaurantMessageEntity) {
        try {
            restaurantMessageRepository.saveAndFlush(restaurantMessageEntity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean CheckApply(RestaurantMessageEntity restaurantMessageEntity) {
        List<ApplyEntity> applyEntities = applyRepository.findByApprovedAndRestaurantMessageEntity(false,restaurantMessageEntity);
        return applyEntities.size() == 0;
    }
}
