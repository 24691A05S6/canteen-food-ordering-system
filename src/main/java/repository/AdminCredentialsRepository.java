package com.canteen.canteen.repository;

import com.canteen.canteen.entity.AdminCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCredentialsRepository extends JpaRepository<AdminCredentials, Long> {
}