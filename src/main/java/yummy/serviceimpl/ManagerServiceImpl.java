package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.AccountRepository;
import yummy.dao.UserRepository;
import yummy.dao.YummyRepository;
import yummy.entity.AccountEntity;
import yummy.entity.YummyEntity;
import yummy.service.ManagerService;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:29 2019/1/18
 */
@Service
public class ManagerServiceImpl extends PublicServiceImpl implements ManagerService {
    private final YummyRepository yummyRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ManagerServiceImpl(UserRepository userRepository, YummyRepository yummyRepository, AccountRepository accountRepository) {
        this.yummyRepository = yummyRepository;
        this.repository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean makeAccount(Double pay) {
        AccountEntity accountEntity = new AccountEntity();
        YummyEntity yummyEntity = yummyRepository.findOne(1);
        yummyEntity.setBalance(yummyEntity.getBalance() + pay);
        accountEntity.setAccount(pay);
        accountEntity.setYummyEntity(yummyEntity);
        try {
            accountRepository.saveAndFlush(accountEntity);
            yummyRepository.saveAndFlush(yummyEntity);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
