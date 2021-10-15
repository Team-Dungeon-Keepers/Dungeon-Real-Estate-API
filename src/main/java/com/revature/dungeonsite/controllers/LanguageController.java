package com.revature.dungeonsite.controllers;

import java.util.List;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.repositories.LanguageRepository;
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


@RestController
@RequestMapping("/api/language")
public class LanguageController {

	private final LanguageRepository lr;

	public LanguageController(LanguageRepository lr) {
		this.lr = lr;
	}

	public ResponseEntity<List<Language>> findall() {
		return ResponseEntity.ok(lr.findAll());
	}

	private Language getNeoLang(@PathVariable("id") Long langID) throws ResourceNotFoundException {
		return lr.findById(langID)
				.orElseThrow(
						() -> new ResourceNotFoundException("Language not found for ID: " + langID)
				);
	}

	@GetMapping("/{languageid}")
	public ResponseEntity<Language> findById(@PathVariable("languageid") long languageid) throws ResourceNotFoundException {
		return ResponseEntity.ok(getNeoLang(languageid));
	}
	
	@PutMapping
	public ResponseEntity<Language> update(@RequestBody Language language){
		if (language.getLanguageid()==0) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(language);
	}
	
	@PostMapping
	public ResponseEntity<Language> create(@RequestBody Language language){
		if (language.getLanguageid()==0) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(lr.save(language));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@PathVariable("languageid") long languageid) throws ResourceNotFoundException {
		lr.delete(getNeoLang(languageid));
		
		return ResponseEntity.ok().build();
	}
}
