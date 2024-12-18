package io.trishul.user.service.user.service.status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.trishul.user.status.UserStatus;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long>, JpaSpecificationExecutor<UserStatus> {
}
