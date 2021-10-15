package com.revature.dungeonsite.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dungeonsite.models.Phone;
import com.revature.dungeonsite.services.PhoneService;


@RestController
@RequestMapping("/api/phone")
public class PhoneController {

	@Autowired
	private PhoneService phoneService;
	
	public ResponseEntity<List<Phone>> findall() {
		return ResponseEntity.ok(phoneService.getAll());
	}
	
	@GetMapping("/{phoneid}")
	public ResponseEntity<Phone> findById(@PathVariable("phoneid") long phoneid){
		
		Optional<Phone> optional = phoneService.getById(phoneid);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Phone> update(@RequestBody Phone phone){
		if (phone.getPhoneid()==0) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(phoneService.update(phone));
	}
	
	@GetMapping("/number/{number}")
	public ResponseEntity<Phone> findByNumber(@PathVariable("number") long number){
		
		Optional<Phone> optional = phoneService.findByNumber(number);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Phone> create(@RequestBody Phone phone){
		if (phone.getPhoneid()==0) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(phoneService.update(phone));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@PathVariable("phoneid") int phoneid){
		phoneService.delete(phoneid);
		
		return ResponseEntity.ok().build();
	}
}
