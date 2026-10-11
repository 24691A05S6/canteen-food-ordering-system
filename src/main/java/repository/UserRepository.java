package com.canteen.canteen.repository;

import com.canteen.canteen.entity.Role;
import com.canteen.canteen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByStudentId(String studentId);

    Optional<User> findByEmailAndRole(String email, Role role);
}