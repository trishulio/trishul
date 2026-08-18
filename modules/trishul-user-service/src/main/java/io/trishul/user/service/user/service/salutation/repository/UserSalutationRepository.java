package io.trishul.user.service.user.service.salutation.repository;

import io.trishul.repo.jpa.repository.ExtendedRepository;
import io.trishul.user.salutation.model.UserSalutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserSalutationRepository extends JpaRepository<UserSalutation, Long>,
    JpaSpecificationExecutor<UserSalutation>, ExtendedRepository<Long> {
  @Override
  @Query("select count(us) > 0 from user_salutation us where us.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from user_salutation us where us.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
