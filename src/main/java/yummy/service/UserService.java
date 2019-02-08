package yummy.service;

import yummy.entity.SysRoleEntity;
import yummy.entity.UserEntity;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 22:07 2019/1/30
 */
public interface UserService extends PublicService{
    UserEntity findByLoginToken(String loginToken);
    SysRoleEntity findRoleById(Integer id);
}
