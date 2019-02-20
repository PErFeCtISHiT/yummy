package yummy.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.AccountRepository;
import yummy.dao.ApplyRepository;
import yummy.dao.UserRepository;
import yummy.dao.YummyRepository;
import yummy.entity.AccountEntity;
import yummy.entity.ApplyEntity;
import yummy.entity.RestaurantMessageEntity;
import yummy.entity.YummyEntity;
import yummy.service.ManagerService;

import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:29 2019/1/18
 */
@Service
public class ManagerServiceImpl extends PublicServiceImpl implements ManagerService {
    private final YummyRepository yummyRepository;
    private final AccountRepository accountRepository;
    private final ApplyRepository applyRepository;

    @Autowired
    public ManagerServiceImpl(UserRepository userRepository, YummyRepository yummyRepository, AccountRepository accountRepository, ApplyRepository applyRepository) {
        this.yummyRepository = yummyRepository;
        this.repository = userRepository;
        this.accountRepository = accountRepository;
        this.applyRepository = applyRepository;
    }

    @Override
    public boolean makeAccount(Double pay, RestaurantMessageEntity restaurantMessageEntity) {
        AccountEntity accountEntity = new AccountEntity();
        YummyEntity yummyEntity = yummyRepository.findOne(1);
        yummyEntity.setBalance(yummyEntity.getBalance() + pay);
        accountEntity.setAccount(pay);
        accountEntity.setYummyEntity(yummyEntity);
        accountEntity.setRestaurantMessageEntity(restaurantMessageEntity);
        try {
            accountRepository.saveAndFlush(accountEntity);
            yummyRepository.saveAndFlush(yummyEntity);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<ApplyEntity> findUnCheckedApply() {
        return applyRepository.findByApproved(false);
    }

    @Override
    public ApplyEntity findApplyById(Integer id) {
        return applyRepository.findOne(id);
    }

    @Override
    public boolean deleteApply(Integer id) {
        try {
            applyRepository.delete(id);
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyApply(ApplyEntity applyEntity) {
        try {
            applyRepository.saveAndFlush(applyEntity);
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<AccountEntity> findUnApprovedAccount() {
        return accountRepository.findByApprovedAndAccountGreaterThanEqual(false,0.0);
    }

    @Override
    public AccountEntity findAccountById(Integer id) {
        return accountRepository.findOne(id);
    }

    @Override
    public boolean modifyAccount(AccountEntity accountEntity) {
        try {
            accountRepository.saveAndFlush(accountEntity);
        }catch (Exception e) {
            return false;
        }
        return true;
    }
}
