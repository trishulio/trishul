package io.trishul.user.service.user.service.status.repository;

import io.trishul.user.status.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserStatusRepository
    extends JpaRepository<UserStatus, Long>, JpaSpecificationExecutor<UserStatus> {
}
