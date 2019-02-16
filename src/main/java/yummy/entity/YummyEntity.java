package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 15:31 2019/2/16
 */
@Entity
@Table(name = "yummy",schema = "yummy")
public class YummyEntity {
    private Integer id;
    private Double balance;
    private Set<AccountEntity> accountEntities;

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
    @Column(name = "yummy_balance",nullable = false)
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @OneToMany(mappedBy = "yummyEntity",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JsonIgnore
    public Set<AccountEntity> getAccountEntities() {
        return accountEntities;
    }

    public void setAccountEntities(Set<AccountEntity> accountEntities) {
        this.accountEntities = accountEntities;
    }
}
