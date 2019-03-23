package yummy.controller.usercontroller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import yummy.entity.*;
import yummy.service.OrderService;
import yummy.service.RestaurantService;
import yummy.service.UserService;
import yummy.util.JsonHelper;
import yummy.util.NamedContext;
import yummy.util.PasswordHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 16:50 2019/2/2
 */
@Controller
@RequestMapping("restaurant")
public class RestaurantController {
    private final UserService userService;

    private final RestaurantService restaurantService;

    private final OrderService orderService;
    @Autowired
    public RestaurantController(UserService userService, RestaurantService restaurantService, OrderService orderService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public void signUp(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        userEntity.setRestaurantMessageEntity(new RestaurantMessageEntity());
        userEntity.setStatus(NamedContext.ACTIVE);
        userEntity.setSysRoleEntity(userService.findRoleById(2));
        userService.add(userEntity);
        String algorithmName = "md5";
        String loginToken = new SimpleHash(algorithmName, userEntity.getId().toString()).toHex().substring(0, 7);
        userEntity.setLoginToken(loginToken);
        PasswordHelper.encryptPassword(userEntity);
        ApplyEntity applyEntity = new ApplyEntity();
        applyEntity.setRestaurantMessageEntity(userEntity.getRestaurantMessageEntity());
        applyEntity.setLoginToken(userEntity.getLoginToken());
        JSONObject ret = new JSONObject();
        if (!userService.modify(userEntity) || !restaurantService.addApply(applyEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
            ret.put(NamedContext.LOGINTOKEN, userEntity.getLoginToken());
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("restaurant")
    @RequestMapping(value = "modifyRestaurant", method = RequestMethod.POST)
    public void modifyRestaurant(@RequestBody ApplyEntity applyEntity, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        applyEntity.setRestaurantMessageEntity(userEntity.getRestaurantMessageEntity());
        applyEntity.setLoginToken(userEntity.getLoginToken());
        if (!restaurantService.addApply(applyEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("restaurant")
    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public void addProduct(@RequestBody ProductEntity productEntity,HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        if(CheckApply(userEntity)){
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }
        JSONArray array = (JSONArray) request.getSession(true).getAttribute(NamedContext.PRODUCTS);
        array.put(new JSONObject(productEntity));
        request.getSession(true).setAttribute(NamedContext.PRODUCTS,array);
        productEntity.setRestaurant(userEntity);
        if (!restaurantService.addProduct(productEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("restaurant")
    @RequestMapping(value = "addOrder", method = RequestMethod.POST)
    public void addOrder(@RequestBody OrderEntity orderEntity, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        System.out.println(orderEntity.getPidList());
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        if(CheckApply(userEntity)){
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }
        orderEntity.setRestaurant(userEntity);
        orderEntity.setPidList(restaurantService.modifyPidList(orderEntity.getPidList()));
        if (!orderService.add(orderEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }
    private boolean CheckApply(UserEntity userEntity) {
        return !restaurantService.CheckApply(userEntity.getRestaurantMessageEntity());
    }

    @RequiresRoles("restaurant")
    @RequestMapping(value = "/getDeliveredOrder", method = RequestMethod.GET)
    public void getDeliveredOrder(HttpServletRequest request, HttpServletResponse response) {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        List<OrderEntity> orderEntities = orderService.findByRestaurantAndStatus(userEntity, NamedContext.DELIVERED);
        for (OrderEntity orderEntity : orderEntities) {
            orderEntity.setRestaurant(null);
            orderEntity.setMember(null);
        }
        JSONArray orderArray = new JSONArray(orderEntities);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        request.getSession(true).setAttribute(NamedContext.ORDERS, orderArray);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("restaurant")
    @RequestMapping(value = "/getStat", method = RequestMethod.POST)
    public void getStat(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        JSONObject object = JsonHelper.requestToJson(request);
        String status = object.getString(NamedContext.STATUS);
        String orderDate = object.getString(NamedContext.ORDERDATE);
        String price = object.getString(NamedContext.PRICE);
        String memberLevel = object.getString(NamedContext.MEMBERLEVEL);
        List<OrderEntity> orderEntities = orderService.findUserOrderByRestaurant(userEntity);
        orderEntities = orderService.filterByStatusAndDateAndPrice(orderEntities,status,orderDate,price);
        orderEntities = orderService.filterByMemberLevel(orderEntities,memberLevel);
        for (OrderEntity orderEntity : orderEntities) {
            orderEntity.setRestaurant(null);
            orderEntity.setMember(null);
        }
        JSONArray orderArray = new JSONArray(orderEntities);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        request.getSession(true).setAttribute(NamedContext.ORDERS, orderArray);
        JsonHelper.jsonToResponse(response, ret);
    }
}
