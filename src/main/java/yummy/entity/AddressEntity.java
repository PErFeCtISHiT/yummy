package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:27 2019/1/17
 */
@Entity
@Table(name = "user_address",schema = "yummy")
public class AddressEntity {
    private Integer id;
    private MemberMessageEntity memberMessageEntity;
    private Double longitude;
    private Double latitude;
    private String addressName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    public MemberMessageEntity getMemberMessageEntity() {
        return memberMessageEntity;
    }

    public void setMemberMessageEntity(MemberMessageEntity memberMessageEntity) {
        this.memberMessageEntity = memberMessageEntity;
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
}
