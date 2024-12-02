package io.trishul.user.service.user.service.repository.role.binding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.trishul.user.role.binding.model.UserRoleBinding;

public interface UserRoleBindingRepository extends JpaRepository<UserRoleBinding, Long>, JpaSpecificationExecutor<UserRoleBinding> {
}
