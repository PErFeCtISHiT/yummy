package yummy.service;

import yummy.entity.ApplyEntity;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:29 2019/1/18
 */
public interface RestaurantService extends PublicService{
    boolean addApply(ApplyEntity applyEntity);
}
