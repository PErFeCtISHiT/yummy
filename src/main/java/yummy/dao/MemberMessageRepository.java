package yummy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yummy.entity.MemberMessageEntity;

import javax.persistence.Table;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:41 2019/1/28
 */
@Repository
@Transactional
@Table(name = "member_message")
public interface MemberMessageRepository extends JpaRepository<MemberMessageEntity,Integer> {
}
