package yummy.service;

import yummy.entity.AccountEntity;
import yummy.entity.ApplyEntity;
import yummy.entity.RestaurantMessageEntity;

import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:29 2019/1/18
 */
public interface ManagerService extends PublicService{
    boolean makeAccount(Double pay, RestaurantMessageEntity restaurantMessageEntity);

    List<ApplyEntity> findUnCheckedApply();

    ApplyEntity findApplyById(Integer id);

    boolean deleteApply(Integer id);

    boolean modifyApply(ApplyEntity applyEntity);

    List<AccountEntity> findUnApprovedAccount();

    AccountEntity findAccountById(Integer id);

    boolean modifyAccount(AccountEntity accountEntity);
}
