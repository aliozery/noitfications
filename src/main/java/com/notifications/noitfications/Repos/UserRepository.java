package com.notifications.noitfications.Repos;

import com.notifications.noitfications.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByName(String name);
}
