package yummy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Date account_date = new Date(new java.util.Date().getTime());
    private YummyEntity yummyEntity;

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
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    @Basic
    @Column(name = "account_date",nullable = false)
    public Date getAccount_date() {
        return account_date;
    }

    public void setAccount_date(Date account_date) {
        this.account_date = account_date;
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
}
