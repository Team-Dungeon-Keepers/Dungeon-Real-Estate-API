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

import com.revature.dungeonsite.models.Language;
import com.revature.dungeonsite.services.LanguageService;


@RestController
@RequestMapping("/api/language")
public class LanguageController {

	@Autowired 
	private LanguageService languageService;
	
	public ResponseEntity<List<Language>> findall() {
		return ResponseEntity.ok(languageService.getAll());
	}
	
	@GetMapping("/{languageid}")
	public ResponseEntity<Language> findById(@PathVariable("languageid") int languageid){
		
		Optional<Language> optional = languageService.getById(languageid);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Language> update(@RequestBody Language language){
		if (language.getLanguageid()==0) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(languageService.update(language));
	}
	
	@PostMapping
	public ResponseEntity<Language> create(@RequestBody Language language){
		if (language.getLanguageid()==0) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(languageService.update(language));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@PathVariable("languageid") int languageid){
		languageService.delete(languageid);
		
		return ResponseEntity.ok().build();
	}
}
