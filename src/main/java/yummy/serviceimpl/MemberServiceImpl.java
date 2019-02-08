package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.MemberMessageRepository;
import yummy.dao.SysRoleRepository;
import yummy.dao.UserRepository;
import yummy.entity.MemberMessageEntity;
import yummy.entity.SysRoleEntity;
import yummy.entity.UserEntity;
import yummy.service.MemberService;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:30 2019/1/18
 */
@Service
public class MemberServiceImpl extends PublicServiceImpl implements MemberService {

    private final MemberMessageRepository memberMessageRepository;
    @Autowired
    public MemberServiceImpl(UserRepository userRepository, MemberMessageRepository memberMessageRepository) {
        this.memberMessageRepository = memberMessageRepository;
        this.repository = userRepository;
    }


    @Override
    public boolean saveMemberMessage(MemberMessageEntity memberMessageEntity) {
        try {
            memberMessageRepository.saveAndFlush(memberMessageEntity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
