package com.revature.dungeonsite.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dungeonsite.repositories.PhoneRepository;
import com.revature.dungeonsite.models.Phone;

@Service
public class PhoneService {
	
	@Autowired
	private PhoneRepository phoneRepo;
	
	public List<Phone> getAll(){
		return phoneRepo.findAll();
	}
	
	public Optional<Phone> getById(long id) {
		return phoneRepo.findById(id);
	}
	
	public Phone create(Phone phone) {
		return phoneRepo.save(phone);
	}
	
	public Phone update(Phone phone) {
		return phoneRepo.save(phone); 
	}
	
	public void delete(long id) {
		phoneRepo.deleteById(id);
	}
	
	public Optional<Phone> findByNumber(long number) {
		return phoneRepo.findByNumber(number);
	}
}
