package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import yummy.util.JsonHelper;

import javax.persistence.*;
import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:17 2019/1/17
 */
@Entity
@Table(name = "member_message",schema = "yummy")
public class MemberMessageEntity {
    private Integer id;
    private String telephone = "null";
    private String memberName = "null";
    private Set<AddressEntity> addressEntitySet;
    private Integer level = 1;
    private Double consume = 0.0;
    private Double balance = 500.0;
    private AddressEntity mainAddress;
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
    @Column(name = "telephone",columnDefinition = "varchar(20) default 'null'")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "member_name",columnDefinition = "varchar(255) default 'null'")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @OneToMany(mappedBy = "memberMessageEntity",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JsonIgnore
    public Set<AddressEntity> getAddressEntitySet() {
        return addressEntitySet;
    }

    public void setAddressEntitySet(Set<AddressEntity> addressEntitySet) {
        this.addressEntitySet = addressEntitySet;
    }

    @Basic
    @Column(name = "member_level", nullable = false,columnDefinition = "int(11) default '1'")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "consume", nullable = false,columnDefinition = "decimal(10,2) default '0.0'")
    public Double getConsume() {
        return JsonHelper.scale(consume);
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    @Basic
    @Column(name = "balance", nullable = false)
    public Double getBalance() {
        return JsonHelper.scale(balance);
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    public AddressEntity getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(AddressEntity mainAddress) {
        this.mainAddress = mainAddress;
    }
}
