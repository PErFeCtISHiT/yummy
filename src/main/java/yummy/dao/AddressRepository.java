package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.AddressEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:01 2019/1/27
 */
@Repository
@Transactional
@Table(name = "user_address")
public interface AddressRepository extends JpaRepository<AddressEntity,Integer> {
    AddressEntity findByAddressName(String address);
}
