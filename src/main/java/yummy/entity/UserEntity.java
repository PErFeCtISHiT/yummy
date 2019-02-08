package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 10:59 2019/1/17
 */
@Entity
@Table(name = "user",schema = "yummy")
public class UserEntity {
    private Integer id;
    private String loginToken = "";//会员是邮箱，餐厅是识别码
    private SysRoleEntity sysRoleEntity;//用户角色
    private String status = "inactive";//active,destroyed,inactive
    private MemberMessageEntity memberMessageEntity;
    private RestaurantMessageEntity restaurantMessageEntity;
    private String salt = "";
    private String userPassword;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login_token", nullable = false)
    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    public SysRoleEntity getSysRoleEntity() {
        return sysRoleEntity;
    }

    public void setSysRoleEntity(SysRoleEntity sysRoleEntity) {
        this.sysRoleEntity = sysRoleEntity;
    }


    @Basic
    @Column(name = "user_status", nullable = false,columnDefinition = "varchar(10) default 'inactive'")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "member_message_id")
    @JsonIgnore
    public MemberMessageEntity getMemberMessageEntity() {
        return memberMessageEntity;
    }

    public void setMemberMessageEntity(MemberMessageEntity memberMessageEntity) {
        this.memberMessageEntity = memberMessageEntity;
    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_message_id")
    @JsonIgnore
    public RestaurantMessageEntity getRestaurantMessageEntity() {
        return restaurantMessageEntity;
    }

    public void setRestaurantMessageEntity(RestaurantMessageEntity restaurantMessageEntity) {
        this.restaurantMessageEntity = restaurantMessageEntity;
    }

    @Basic
    @Column(name = "salt", nullable = false)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "user_password", nullable = false)
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
