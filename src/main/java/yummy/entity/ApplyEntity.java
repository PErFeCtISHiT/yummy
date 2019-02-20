package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 19:04 2019/2/3
 */
@Entity
@Table(name = "restaurant_apply",schema = "yummy")
public class ApplyEntity {
    private Integer id;
    private String restaurantName;
    private String restaurantType;
    private String addressName;
    private Double longitude;
    private Double latitude;
    private RestaurantMessageEntity restaurantMessageEntity;
    private Date applyDate = new Date(new java.util.Date().getTime());
    private boolean approved = false;

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
    @Column(name = "restaurant_name")
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    @Basic
    @Column(name = "restaurant_type")
    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }
    @Basic
    @Column(name = "apply_date")
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    @Basic
    @Column(name = "longitude",nullable = false)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude",nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "address_name",nullable = false)
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }


    @Basic
    @Column(name = "approved",nullable = false)
    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
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
}
