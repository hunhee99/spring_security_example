package com.choi.securitystudy.user.repository;

import com.choi.securitystudy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
