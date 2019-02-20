package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.ApplyEntity;

import javax.persistence.Table;
import java.util.List;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 19:19 2019/2/3
 */
@Repository
@Transactional
@Table(name = "restaurant_apply")
public interface ApplyRepository extends JpaRepository<ApplyEntity,Integer> {
    List<ApplyEntity> findByApproved(Boolean approved);
}
