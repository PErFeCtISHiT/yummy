package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.SysRoleRepository;
import yummy.dao.UserRepository;
import yummy.entity.SysRoleEntity;
import yummy.entity.UserEntity;
import yummy.service.UserService;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 22:08 2019/1/30
 */
@Service
public class UserServiceImpl extends PublicServiceImpl implements UserService {
    private final SysRoleRepository sysRoleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(SysRoleRepository sysRoleRepository, UserRepository userRepository) {
        this.sysRoleRepository = sysRoleRepository;
        this.userRepository = userRepository;
        this.repository = userRepository;
    }


    @Override
    public UserEntity findByLoginToken(String loginToken) {
        return userRepository.findByLoginToken(loginToken);
    }

    @Override
    public SysRoleEntity findRoleById(Integer id) {
        return sysRoleRepository.findOne(id);
    }
}
