package yummy.entity;

import javax.persistence.*;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:11 2019/1/17
 */
@Entity
@Table(name = "sys_role",schema = "yummy")
public class SysRoleEntity {
    private Integer id;//1: member 2: restaurant 3: manager
    private String role;

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
    @Column(name = "role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
