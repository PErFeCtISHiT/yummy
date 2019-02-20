package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import yummy.util.JsonHelper;
import yummy.util.NamedContext;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 23:22 2019/2/7
 */
@Entity
@Table(name = "restaurant_order",schema = "yummy")
public class OrderEntity {
    private Integer id;
    private String orderName = "套餐";
    private String pidList;
    private Date endDate = new Date(new java.util.Date().getTime());
    private Double price = 0.0;
    private Double discount = 0.0;
    private String status = NamedContext.UNORDERED;//unOrdered,unPayed,payed,delivered,canceled
    private UserEntity restaurant;
    private UserEntity member;
    private Time orderTime = new Time(new java.util.Date().getTime());

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
    @Column(name = "pid_list", nullable = false)
    public String getPidList() {
        return pidList;
    }

    public void setPidList(String pidList) {
        this.pidList = pidList;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "order_price", nullable = false)
    public Double getPrice() {
        return JsonHelper.scale(price);
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "order_discount", nullable = false)
    public Double getDiscount() {
        return JsonHelper.scale(discount);
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "order_name", nullable = false)
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Basic
    @Column(name = "order_status", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    public UserEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(UserEntity restaurant) {
        this.restaurant = restaurant;
    }

    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    public UserEntity getMember() {
        return member;
    }

    public void setMember(UserEntity member) {
        this.member = member;
    }

    @Basic
    @Column(name = "order_time")
    public Time getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
    }
}
