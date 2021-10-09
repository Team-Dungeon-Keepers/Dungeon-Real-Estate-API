package com.revature.springskeleton.repositories;

import com.revature.springskeleton.models.YipMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YipRepository extends JpaRepository<YipMessage, Long> {
}
