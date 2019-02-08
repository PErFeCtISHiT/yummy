package yummy.service;

import yummy.entity.MemberMessageEntity;
import yummy.entity.SysRoleEntity;
import yummy.entity.UserEntity;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:29 2019/1/18
 */
public interface MemberService extends PublicService{


    boolean saveMemberMessage(MemberMessageEntity memberMessageEntity);
}
