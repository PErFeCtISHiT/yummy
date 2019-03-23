package yummy;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import yummy.entity.*;
import yummy.service.*;
import yummy.util.NamedContext;
import yummy.util.PasswordHelper;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 17:51 2019/2/21
 */
@Component
public class DataGenerator implements ApplicationRunner {
    private final ManagerService managerService;
    private final UserService userService;
    private final AddressService addressService;
    private final RestaurantService restaurantService;
    @Autowired
    public DataGenerator(ManagerService managerService, UserService userService, AddressService addressService, RestaurantService restaurantService) {
        this.managerService = managerService;
        this.userService = userService;
        this.addressService = addressService;
        this.restaurantService = restaurantService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //generateMember();
        //generateManager();
        //generateRestaurant();
        //generateProduct();
        //generateAccount();
    }
    private void generateMember(){
        for(int i = 0;i < 20;i++){
            UserEntity userEntity = new UserEntity();
            MemberMessageEntity memberMessageEntity = new MemberMessageEntity();
            userEntity.setMemberMessageEntity(memberMessageEntity);
            userEntity.setSysRoleEntity(userService.findRoleById(1));
            userEntity.setLoginToken("12345"+i+"@qq.com");
            userEntity.setStatus(NamedContext.ACTIVE);
            userEntity.setUserPassword("123456");
            PasswordHelper.encryptPassword(userEntity);
            memberMessageEntity.setMemberName("会员"+i);
            memberMessageEntity.setTelephone("123456789");
            userService.add(userEntity);
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setAddressName("address"+i);
            addressEntity.setLatitude(31.5+Math.random());
            addressEntity.setLongitude(118.5+Math.random());
            addressEntity.setMemberMessageEntity(memberMessageEntity);
            addressService.add(addressEntity);
            memberMessageEntity.setMainAddress(addressEntity);
            Set<AddressEntity> addressEntities = new HashSet<>();
            addressEntities.add(addressEntity);
            memberMessageEntity.setAddressEntitySet(addressEntities);
            userService.modify(userEntity);
        }
    }
    private void generateManager(){
        UserEntity userEntity = new UserEntity();
        userEntity.setStatus(NamedContext.ACTIVE);
        userEntity.setSysRoleEntity(userService.findRoleById(3));
        userEntity.setUserPassword("123456");
        userEntity.setLoginToken("manager");
        PasswordHelper.encryptPassword(userEntity);
        userService.add(userEntity);
    }
    private void generateRestaurant(){
        for(int i = 0;i < 20;i++) {
            UserEntity userEntity = new UserEntity();
            RestaurantMessageEntity restaurantMessageEntity = new RestaurantMessageEntity();
            userEntity.setSysRoleEntity(userService.findRoleById(2));
            String algorithmName = "md5";
            userEntity.setStatus(NamedContext.ACTIVE);
            userEntity.setUserPassword("123456");
            restaurantMessageEntity.setRestaurantName("餐厅" + i);
            restaurantMessageEntity.setRestaurantType("美食");
            userEntity.setLoginToken("");
            userEntity.setRestaurantMessageEntity(restaurantMessageEntity);
            userService.add(userEntity);
            String loginToken = new SimpleHash(algorithmName, userEntity.getId().toString()).toHex().substring(0, 7);
            userEntity.setLoginToken(loginToken);
            PasswordHelper.encryptPassword(userEntity);
            userService.modify(userEntity);
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setAddressName("restaurantAddress"+i);
            addressEntity.setLatitude(31.5+Math.random());
            addressEntity.setLongitude(118.5+Math.random());
            addressService.add(addressEntity);
            restaurantMessageEntity.setAddressEntity(addressEntity);
            userService.modify(userEntity);
        }
    }
    private void generateProduct(){
        UserEntity userEntity = userService.findByLoginToken("f766406");
        for(int i = 0;i < 20;i++){
            ProductEntity productEntity = new ProductEntity();
            productEntity.setNum((int) (Math.random() * 50));
            productEntity.setRestaurant(userEntity);
            java.util.Date d = new java.util.Date();
            d.setYear(d.getYear()+1);
            Date date = new Date(d.getTime());
            productEntity.setEndDate(date);
            productEntity.setPrice(Math.random() * 100);
            productEntity.setProductName("product"+i);
            productEntity.setType("美食");
            restaurantService.addProduct(productEntity);
        }
    }
    private void generateAccount(){
        for(int i = 0;i < 12;i++){
            AccountEntity accountEntity = new AccountEntity();
            java.util.Date d = new java.util.Date();
            d.setMonth(i+1);
            Date date = new Date(d.getTime());
            accountEntity.setRestaurantMessageEntity(restaurantService.findRestaurantMessageById(29));
            accountEntity.setYummyEntity(managerService.findYummy());
            accountEntity.setAccount(Math.random() * 2000 - 1000);
            accountEntity.setAccountDate(date);
            managerService.modifyAccount(accountEntity);
        }
    }
}
