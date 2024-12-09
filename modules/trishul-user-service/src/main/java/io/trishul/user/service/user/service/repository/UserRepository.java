package io.trishul.user.service.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.trishul.repo.jpa.repository.aggregation.ExtendedRepository;
import io.trishul.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, ExtendedRepository<Long> {
    @Override
    @Query("select count(i) > 0 from user u where u.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Override
    @Modifying
    @Query("delete from user u where u.id in (:ids)")
    int deleteByIds(Iterable<Long> ids);
}
