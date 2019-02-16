package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.AccountEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 16:53 2019/2/16
 */
@Repository
@Transactional
@Table(name = "yummy_account")
public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {
}
