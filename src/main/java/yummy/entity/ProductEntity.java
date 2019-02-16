package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 23:18 2019/2/7
 */
@Entity
@Table(name = "restaurant_product",schema = "yummy")
public class ProductEntity {
    private Integer id;
    private String productName;
    private Integer num;
    private Double price;
    private Date endDate;
    private String type;
    private UserEntity restaurant;
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
    @Column(name = "product_name", nullable = false)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "product_num", nullable = false)
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Basic
    @Column(name = "product_price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
    @Column(name = "product_type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
