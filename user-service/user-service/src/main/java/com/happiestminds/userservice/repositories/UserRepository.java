package com.happiestminds.userservice.repositories;

import com.happiestminds.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
