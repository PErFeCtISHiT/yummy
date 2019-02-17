package yummy.controller.usercontroller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import yummy.entity.*;
import yummy.service.*;
import yummy.util.AddressHelper;
import yummy.util.JsonHelper;
import yummy.util.NamedContext;
import yummy.util.PasswordHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:07 2019/1/18
 */
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    private final JavaMailSender mailSender;
    private final AddressService addressService;

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final ManagerService managerService;

    @Autowired
    public MemberController(MemberService memberService, JavaMailSender mailSender, AddressService addressService, UserService userService, RestaurantService restaurantService, OrderService orderService, ManagerService managerService) {
        this.memberService = memberService;
        this.mailSender = mailSender;
        this.addressService = addressService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
        this.managerService = managerService;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public void signUp(@RequestBody UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        PasswordHelper.encryptPassword(userEntity);
        userEntity.setSysRoleEntity(userService.findRoleById(1));
        userEntity.setMemberMessageEntity(new MemberMessageEntity());
        UserEntity user = userService.findByLoginToken(userEntity.getLoginToken());
        JSONObject ret = new JSONObject();
        String regex = ".*@.*";
        if (user != null) {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.REPEATUSER);
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        } else if (!Pattern.matches(regex, userEntity.getLoginToken())) {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.MAILINVALID);
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }

        memberService.add(userEntity);

        StringBuilder sb = new StringBuilder("点击下面链接激活账号\n");
        sb.append("http://localhost:8080/member/activate?&email=");
        sb.append(userEntity.getLoginToken());
        sb.append("&salt=");
        sb.append(userEntity.getSalt());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("849798320@qq.com");
        message.setTo(userEntity.getLoginToken());
        message.setSubject("会员邮箱激活");
        message.setText(sb.toString());

        mailSender.send(message);
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public void activate(@RequestParam String email, @RequestParam String salt, HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        UserEntity userEntity = userService.findByLoginToken(email);
        if (userEntity != null && userEntity.getSalt().equals(salt)) {
            userEntity.setStatus(NamedContext.ACTIVE);
            memberService.modify(userEntity);
            request.getServletContext().setAttribute(NamedContext.USER, userEntity);
            request.getRequestDispatcher("/user/info.jsp").forward(request, response);
        } else {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.ACTIVEFAILED);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

    }

    @RequiresRoles("member")
    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    public void modifyUser(@RequestBody MemberMessageEntity memberMessageEntity, HttpServletRequest request, HttpServletResponse response) {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        MemberMessageEntity oldMessage = userEntity.getMemberMessageEntity();
        oldMessage.setMemberName(memberMessageEntity.getMemberName());
        oldMessage.setTelephone(memberMessageEntity.getTelephone());
        JSONObject ret = new JSONObject();
        if (memberService.saveMemberMessage(oldMessage)) {
            request.getSession(true).setAttribute(NamedContext.USER, userEntity);
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        } else
            ret.put(NamedContext.MES, NamedContext.FAILED);
        JsonHelper.jsonToResponse(response, ret);

    }

    @RequiresRoles("member")
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public void addAddress(@RequestBody AddressEntity addressEntity, HttpServletRequest request, HttpServletResponse response) {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        addressEntity.setMemberMessageEntity(userEntity.getMemberMessageEntity());
        userEntity.getMemberMessageEntity().getAddressEntitySet().add(addressEntity);
        JSONObject ret = new JSONObject();

        if (addressService.add(addressEntity)) {
            HttpSession session = request.getSession(true);
            session.setAttribute(NamedContext.USER, userEntity);
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        } else
            ret.put(NamedContext.MES, NamedContext.FAILED);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/removeAddress", method = RequestMethod.POST)
    public void removeAddress(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = JsonHelper.requestToJson(request);
        JSONArray array = new JSONArray(jsonObject.getJSONArray(NamedContext.ADDRESS).toString());
        HttpSession session = request.getSession(true);
        UserEntity userEntity = (UserEntity) session.getAttribute(NamedContext.USER);
        for (Object o : array) {
            String address = (String) o;
            AddressEntity addressEntity = addressService.findAddressByAddressName(address);
            userEntity.getMemberMessageEntity().getAddressEntitySet().removeIf(addressEntity1 -> addressEntity1.getId().equals(addressEntity.getId()));
            addressService.delete(addressEntity);
        }
        session.setAttribute(NamedContext.USER, userEntity);

        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/mainAddress", method = RequestMethod.GET)
    public void mainAddress(@RequestParam String addressName, HttpServletResponse response) {
        AddressEntity addressEntity = addressService.findAddressByAddressName(addressName);
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        userEntity.getMemberMessageEntity().setMainAddress(addressEntity);
        JSONObject ret = new JSONObject();
        if (!memberService.saveMemberMessage(userEntity.getMemberMessageEntity())) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.ADDRESSID, addressEntity.getId());
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/destroyUser", method = RequestMethod.GET)
    public void destroyUser(HttpServletResponse response) {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        userEntity.setStatus(NamedContext.DESTROYED);
        JSONObject ret = new JSONObject();
        SecurityUtils.getSubject().logout();
        if (!memberService.modify(userEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    public void getProducts(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
        RestaurantMessageEntity restaurantMessageEntity = restaurantService.findRestaurantMessageById(id);
        UserEntity restaurant = restaurantMessageEntity.getRestaurantEntity();
        Set<ProductEntity> productEntities = restaurant.getProductEntities();
        Date date = new Date(new java.util.Date().getTime());
        Set<ProductEntity> showProducts = new HashSet<>();
        for (ProductEntity productEntity : productEntities) {
            if (productEntity.getEndDate().after(date) && productEntity.getNum() > 0) {
                showProducts.add(productEntity);
                productEntity.setRestaurant(null);
            }
        }
        JSONArray productArray = new JSONArray(showProducts);
        for (ProductEntity productEntity : productEntities)
            productEntity.setRestaurant(restaurant);
        List<OrderEntity> orderEntities = restaurantService.findOrders(restaurant);
        List<OrderEntity> orderEntityList = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getStatus().equals(NamedContext.UNORDERED) && orderEntity.getEndDate().after(date)) {
                orderEntityList.add(orderEntity);
            }
        }
        JSONArray orderArray = new JSONArray(orderEntityList);
        JSONObject object = new JSONObject();
        object.put(NamedContext.PRODUCTS, productArray);
        object.put(NamedContext.ORDERS, orderArray);
        request.getSession(true).setAttribute(NamedContext.ALLPRODUCTS, object);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/addSingleOrder", method = RequestMethod.POST)
    public void addSingleOrder(HttpServletRequest request, HttpServletResponse response) {
        JSONObject para = JsonHelper.requestToJson(request);
        OrderEntity orderEntity = new OrderEntity();
        Time time = new Time(new java.util.Date().getTime());
        orderEntity.setOrderTime(time);
        orderEntity.setPrice(para.getDouble(NamedContext.PRICE));
        orderEntity.setPidList(restaurantService.modifyPidList(para.getString(NamedContext.PIDLIST)));
        JSONObject ret = new JSONObject();
        if (!restaurantService.removeProducts(orderEntity.getPidList())) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }
        RestaurantMessageEntity restaurantMessageEntity = restaurantService.findRestaurantMessageById(para.getInt(NamedContext.RESTAURANTID));
        UserEntity restaurant = restaurantMessageEntity.getRestaurantEntity();
        orderEntity.setRestaurant(restaurant);
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        orderEntity.setMember(userEntity);
        orderEntity.setStatus(NamedContext.UNPAYED);
        Double sentMinute = AddressHelper.calculateDistance(userEntity.getMemberMessageEntity().getMainAddress(), restaurant.getRestaurantMessageEntity().getAddressEntity());

        addOrder(request, orderEntity, userEntity, ret, sentMinute);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public void pay(@RequestParam Integer orderId, HttpServletResponse response) {
        OrderEntity orderEntity = (OrderEntity) orderService.findByID(orderId);
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        MemberMessageEntity memberMessageEntity = userEntity.getMemberMessageEntity();
        orderEntity.setDiscount(orderEntity.getDiscount() + memberMessageEntity.getLevel() / 100);
        Double pay = orderEntity.getPrice() * (1 - orderEntity.getDiscount());
        JSONObject ret = new JSONObject();
        if (memberMessageEntity.getBalance() < pay) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }
        memberMessageEntity.setBalance(memberMessageEntity.getBalance() - pay);
        memberMessageEntity.setConsume(memberMessageEntity.getConsume() + pay);
        memberMessageEntity.setLevel((int) (Math.log(memberMessageEntity.getConsume()) / Math.log(10)));
        orderEntity.setStatus(NamedContext.PAYED);
        if (!managerService.makeAccount(pay) || !memberService.saveMemberMessage(memberMessageEntity) || !orderService.modify(orderEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/payFailed", method = RequestMethod.GET)
    public void payFailed(@RequestParam Integer orderId, HttpServletResponse response) {
        OrderEntity orderEntity = (OrderEntity) orderService.findByID(orderId);
        JSONObject ret = new JSONObject();
        if (!restaurantService.addProducts(orderEntity.getPidList())) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }
        if (!orderService.delete(orderEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/addRestaurantOrder", method = RequestMethod.GET)
    public void addRestaurantOrder(@RequestParam Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        OrderEntity orderEntity = (OrderEntity) orderService.findByID(orderId);
        OrderEntity order = new OrderEntity();
        order.setPidList(orderEntity.getPidList());
        order.setStatus(NamedContext.UNPAYED);
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        order.setMember(userEntity);
        order.setRestaurant(orderEntity.getRestaurant());
        order.setPrice(orderEntity.getPrice());
        order.setDiscount(orderEntity.getDiscount());
        Time time = new Time(new java.util.Date().getTime());
        orderEntity.setOrderTime(time);
        order.setOrderName(orderEntity.getOrderName());
        JSONObject ret = new JSONObject();
        if (!restaurantService.removeProducts(orderEntity.getPidList())) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }

        Double sentMinute = AddressHelper.calculateDistance(userEntity.getMemberMessageEntity().getMainAddress(), order.getRestaurant().getRestaurantMessageEntity().getAddressEntity());

        addOrder(request, order, userEntity, ret, sentMinute);
        JsonHelper.jsonToResponse(response, ret);
    }

    private void addOrder(HttpServletRequest request, OrderEntity order, UserEntity userEntity, JSONObject ret, Double sentMinute) {
        if (sentMinute >= 30 || !orderService.add(order)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute(NamedContext.ORDERID, order.getId());
            session.setAttribute(NamedContext.SENTMINUTE, JsonHelper.scale(sentMinute));
            session.setAttribute(NamedContext.MAIL, userEntity.getLoginToken());
            session.setAttribute(NamedContext.BALANCE, JsonHelper.scale(userEntity.getMemberMessageEntity().getBalance()));
            session.setAttribute(NamedContext.PAY, JsonHelper.scale(order.getPrice()));
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/getUndeliveredOrder", method = RequestMethod.GET)
    public void getUndeliveredOrder(HttpServletRequest request, HttpServletResponse response) {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        List<OrderEntity> orderEntities = orderService.findByMemberAndStatus(userEntity, NamedContext.PAYED);
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

    @RequiresRoles("member")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.GET)
    public void cancelOrder(@RequestParam Integer orderId, HttpServletResponse response) {
        OrderEntity orderEntity = (OrderEntity) orderService.findByID(orderId);
        JSONObject ret = new JSONObject();
        orderEntity.setStatus(NamedContext.CANCELED);
        Double cancelPrice = orderService.calculateCancelPrice(orderEntity);
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        MemberMessageEntity memberMessageEntity = userEntity.getMemberMessageEntity();
        memberMessageEntity.setBalance(memberMessageEntity.getBalance() + cancelPrice);
        if (!restaurantService.addProducts(orderEntity.getPidList()) || !memberService.saveMemberMessage(memberMessageEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }
        if (!orderService.modify(orderEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.CANCELPRICE,cancelPrice);
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/acceptOrder", method = RequestMethod.GET)
    public void acceptOrder(@RequestParam Integer orderId, HttpServletResponse response) {
        OrderEntity orderEntity = (OrderEntity) orderService.findByID(orderId);
        JSONObject ret = new JSONObject();
        orderEntity.setStatus(NamedContext.DELIVERED);
        double pay = (orderEntity.getPrice() * (1 - orderEntity.getDiscount())) * 0.95;
        UserEntity restaurant = orderEntity.getRestaurant();
        RestaurantMessageEntity restaurantMessageEntity = restaurant.getRestaurantMessageEntity();
        restaurantMessageEntity.setBalance(restaurantMessageEntity.getBalance() + pay);
        if (!managerService.makeAccount(-pay) || !orderService.modify(orderEntity) || !restaurantService.saveRestaurantMessage(restaurantMessageEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/getStat", method = RequestMethod.POST)
    public void getStat(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        JSONObject object = JsonHelper.requestToJson(request);
        String status = object.getString(NamedContext.STATUS);
        String orderDate = object.getString(NamedContext.ORDERDATE);
        String price = object.getString(NamedContext.PRICE);
        String restaurantType = object.getString(NamedContext.RESTAURANTTYPE);
        List<OrderEntity> orderEntities = orderService.findByMember(userEntity);
        orderEntities = orderService.filterByStatusAndDateAndPrice(orderEntities,status,orderDate,price);
        orderEntities = orderService.filterByRestaurantType(orderEntities,restaurantType);
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
