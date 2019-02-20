package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import yummy.util.JsonHelper;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 16:44 2019/2/16
 */
@Entity
@Table(name = "yummy_account",schema = "yummy")
public class AccountEntity {
    private Integer id;
    private Double account;
    private Date accountDate = new Date(new java.util.Date().getTime());
    private boolean approved = false;
    private YummyEntity yummyEntity;
    private RestaurantMessageEntity restaurantMessageEntity;
    private String restaurantName;
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
    @Column(name = "yummy_account",nullable = false)
    public Double getAccount() {
        return JsonHelper.scale(account);
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    @Basic
    @Column(name = "account_date",nullable = false)
    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }



    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "yummy_id")
    @JsonIgnore
    public YummyEntity getYummyEntity() {
        return yummyEntity;
    }

    public void setYummyEntity(YummyEntity yummyEntity) {
        this.yummyEntity = yummyEntity;
    }

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    public RestaurantMessageEntity getRestaurantMessageEntity() {
        return restaurantMessageEntity;
    }

    public void setRestaurantMessageEntity(RestaurantMessageEntity restaurantMessageEntity) {
        this.restaurantMessageEntity = restaurantMessageEntity;
    }

    @Basic
    @Column(name = "approved",nullable = false)
    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
