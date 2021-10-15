package com.revature.dungeonsite.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.dungeonsite.models.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long>{

	public Optional<Phone> findByUsername(long number);
}
