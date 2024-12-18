package io.trishul.user.service.user.service.salutation.repository;

import io.trishul.user.salutation.model.UserSalutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserSalutationRepository
        extends JpaRepository<UserSalutation, Long>, JpaSpecificationExecutor<UserSalutation> {}
