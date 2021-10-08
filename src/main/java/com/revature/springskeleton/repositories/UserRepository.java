package com.revature.springskeleton.repositories;

import com.revature.springskeleton.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
    public SiteUser findByUsername(String username);
}
