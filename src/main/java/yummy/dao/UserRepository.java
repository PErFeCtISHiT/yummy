package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.SysRoleEntity;
import yummy.entity.UserEntity;

import javax.persistence.Table;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:49 2019/1/17
 */
@Repository
@Transactional
@Table(name = "user")
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByLoginToken(String loginToken);
    List<UserEntity> findBySysRoleEntity(SysRoleEntity sysRoleEntity);
}
