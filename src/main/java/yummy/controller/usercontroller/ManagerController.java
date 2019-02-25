package yummy.controller.usercontroller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import yummy.entity.*;
import yummy.service.ManagerService;
import yummy.service.RestaurantService;
import yummy.service.UserService;
import yummy.util.JsonHelper;
import yummy.util.NamedContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 16:26 2019/2/18
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    private final RestaurantService restaurantService;
    private final UserService userService;

    @Autowired
    public ManagerController(ManagerService managerService, RestaurantService restaurantService, UserService userService) {
        this.managerService = managerService;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @RequiresRoles("manager")
    @RequestMapping(value = "/getRestaurantApply", method = RequestMethod.GET)
    public void getRestaurantApply(HttpServletRequest request, HttpServletResponse response) {
        List<ApplyEntity> applyEntities = managerService.findUnCheckedApply();
        for (ApplyEntity applyEntity : applyEntities) {
            applyEntity.setRestaurantMessageEntity(null);
        }
        JSONArray array = new JSONArray(applyEntities);
        request.getSession(true).setAttribute(NamedContext.APPLIES, array);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("manager")
    @RequestMapping(value = "/approveApply", method = RequestMethod.GET)
    public void approveApply(@RequestParam Integer id, HttpServletResponse response) {
        ApplyEntity applyEntity = managerService.findApplyById(id);
        RestaurantMessageEntity restaurantMessageEntity = applyEntity.getRestaurantMessageEntity();
        AddressEntity addressEntity;
        if (restaurantMessageEntity.getAddressEntity() == null) {
            addressEntity = new AddressEntity();
            restaurantMessageEntity.setAddressEntity(addressEntity);
        } else
            addressEntity = restaurantMessageEntity.getAddressEntity();
        addressEntity.setLatitude(applyEntity.getLatitude());
        addressEntity.setLongitude(applyEntity.getLongitude());
        addressEntity.setAddressName(applyEntity.getAddressName());
        JSONObject ret = new JSONObject();
        restaurantMessageEntity.setRestaurantName(applyEntity.getRestaurantName());
        restaurantMessageEntity.setRestaurantType(applyEntity.getRestaurantType());
        applyEntity.setApproved(true);
        if (!managerService.modifyApply(applyEntity) || !restaurantService.saveRestaurantMessage(restaurantMessageEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);

    }

    @RequiresRoles("manager")
    @RequestMapping(value = "/cancelApply", method = RequestMethod.GET)
    public void cancelApply(@RequestParam Integer id, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        if (!managerService.deleteApply(id)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("manager")
    @RequestMapping(value = "/getRestaurantAccount", method = RequestMethod.GET)
    public void getRestaurantAccount(HttpServletRequest request, HttpServletResponse response) {
        List<AccountEntity> accountEntities = managerService.findUnApprovedAccount();
        for (AccountEntity accountEntity : accountEntities) {
            accountEntity.setRestaurantName(accountEntity.getRestaurantMessageEntity().getRestaurantName());
            accountEntity.setRestaurantMessageEntity(null);
            accountEntity.setYummyEntity(null);
        }
        JSONArray array = new JSONArray(accountEntities);
        request.getSession(true).setAttribute(NamedContext.ACCOUNT, array);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequiresRoles("manager")
    @RequestMapping(value = "/approveAccount", method = RequestMethod.GET)
    public void approveAccount(@RequestParam Integer id, HttpServletResponse response) {
        AccountEntity accountEntity = managerService.findAccountById(id);
        accountEntity.setApproved(true);
        RestaurantMessageEntity restaurantMessageEntity = accountEntity.getRestaurantMessageEntity();
        Double account = accountEntity.getAccount() * 0.95;
        restaurantMessageEntity.setBalance(restaurantMessageEntity.getBalance() + account);
        JSONObject ret = new JSONObject();
        if (!restaurantService.saveRestaurantMessage(restaurantMessageEntity) || !managerService.modifyAccount(accountEntity) || !managerService.makeAccount(-account,restaurantMessageEntity)) {
            ret.put(NamedContext.MES, NamedContext.FAILED);
        } else {
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response, ret);
    }
    @RequiresRoles("manager")
    @RequestMapping(value = "/getRestaurantStat", method = RequestMethod.GET)
    public void getRestaurantStat(HttpServletRequest request, HttpServletResponse response) {
        List<UserEntity> userEntities = userService.findRestaurant();
        JSONObject typeObject = new JSONObject();
        typeObject.put("美食",0);
        typeObject.put("饮品",0);
        typeObject.put("甜品",0);
        JSONArray addressArray = new JSONArray();
        for(UserEntity userEntity : userEntities){
            AddressEntity addressEntity = userEntity.getRestaurantMessageEntity().getAddressEntity();
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            array.put(addressEntity.getLongitude());
            array.put(addressEntity.getLatitude());
            jsonObject.put(NamedContext.NAME,userEntity.getRestaurantMessageEntity().getRestaurantName());
            jsonObject.put(NamedContext.VALUE,array);
            addressArray.put(jsonObject);

            String type = userEntity.getRestaurantMessageEntity().getRestaurantType();
            typeObject.put(type,typeObject.getInt(type)+1);
            userEntity.setSysRoleEntity(null);
            userEntity.setRestaurantMessageEntity(null);
            userEntity.setProductEntities(null);
        }
        JSONArray array = new JSONArray(userEntities);
        request.getSession(true).setAttribute(NamedContext.RESTAURANT,array);
        request.getSession(true).setAttribute(NamedContext.RESTAURANTTYPE,typeObject);
        request.getSession(true).setAttribute(NamedContext.ADDRESS,addressArray);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }
    @RequiresRoles("manager")
    @RequestMapping(value = "/getMemberStat", method = RequestMethod.GET)
    public void getMemberStat(HttpServletRequest request, HttpServletResponse response) {
        List<UserEntity> userEntities = userService.findMember();
        JSONObject typeObject = new JSONObject();
        for(int i = 0;i < 7;i++){
            typeObject.put(String.valueOf(i+1),0);
        }
        JSONArray addressArray = new JSONArray();
        for(UserEntity userEntity : userEntities){
            AddressEntity addressEntity = userEntity.getMemberMessageEntity().getMainAddress();
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            array.put(addressEntity.getLongitude());
            array.put(addressEntity.getLatitude());
            jsonObject.put(NamedContext.NAME,userEntity.getMemberMessageEntity().getMemberName());
            jsonObject.put(NamedContext.VALUE,array);
            addressArray.put(jsonObject);

            Integer level = userEntity.getMemberMessageEntity().getLevel();
            typeObject.put(String.valueOf(level),typeObject.getInt(String.valueOf(level))+1);
            userEntity.setSysRoleEntity(null);
            userEntity.setMemberMessageEntity(null);
            userEntity.setProductEntities(null);
        }
        JSONArray array = new JSONArray(userEntities);
        request.getSession(true).setAttribute(NamedContext.MEMBER,array);
        request.getSession(true).setAttribute(NamedContext.MEMBERLEVEL,typeObject);
        request.getSession(true).setAttribute(NamedContext.ADDRESS,addressArray);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }
    @RequiresRoles("manager")
    @RequestMapping(value = "/getYummyStat", method = RequestMethod.GET)
    public void getYummyStat(HttpServletRequest request, HttpServletResponse response) {
        YummyEntity yummyEntity = managerService.findYummy();
        List<AccountEntity> accountEntities = managerService.findAllAccounts();
        request.getSession(true).setAttribute(NamedContext.YUMMY,yummyEntity.getBalance());
        for(AccountEntity accountEntity : accountEntities){
            accountEntity.setYummyEntity(null);
            accountEntity.setRestaurantMessageEntity(null);
        }
        JSONArray array = new JSONArray(accountEntities);
        request.getSession(true).setAttribute(NamedContext.ACCOUNT,array);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }
}
