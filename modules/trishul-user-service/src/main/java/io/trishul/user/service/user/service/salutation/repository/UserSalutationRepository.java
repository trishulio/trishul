package io.trishul.user.service.user.service.salutation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.trishul.user.salutation.model.UserSalutation;

public interface UserSalutationRepository extends JpaRepository<UserSalutation, Long>, JpaSpecificationExecutor<UserSalutation> {
}
