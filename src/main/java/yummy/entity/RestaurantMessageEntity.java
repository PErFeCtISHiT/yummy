package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:17 2019/1/17
 */
@Entity
@Table(name = "restaurant_message",schema = "yummy")
public class RestaurantMessageEntity {
    private Integer id;
    private String restaurantType = "美食";//美食、饮品、甜品
    private String restaurantName = "";
    private AddressEntity addressEntity;

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
    @Column(name = "restaurant_type", nullable = false)
    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    @Column(name = "restaurant_name")
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
