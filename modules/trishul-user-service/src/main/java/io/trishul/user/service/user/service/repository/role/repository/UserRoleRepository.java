package io.trishul.user.service.user.service.repository.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.trishul.repo.jpa.repository.aggregation.ExtendedRepository;
import io.trishul.user.role.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole>, ExtendedRepository<Long> {
    @Override
    @Query("select count(i) > 0 from user_role ur where ur.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Override
    @Modifying
    @Query("delete from user_role ur where ur.id in (:ids)")
    int deleteByIds(Iterable<Long> ids);
}
