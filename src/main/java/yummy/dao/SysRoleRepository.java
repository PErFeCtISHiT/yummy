package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.SysRoleEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 15:18 2019/1/18
 */
@Repository
@Transactional
@Table(name = "sys_role")
public interface SysRoleRepository extends JpaRepository<SysRoleEntity,Integer> {
}
