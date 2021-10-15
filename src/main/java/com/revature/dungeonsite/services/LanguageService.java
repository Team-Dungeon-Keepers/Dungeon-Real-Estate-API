package com.revature.dungeonsite.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dungeonsite.models.Language;
import com.revature.dungeonsite.repositories.LanguageRepository;



public class LanguageService {


	@Autowired
	private LanguageRepository LangRepo;
	
	public List<Language> getAll(){
		return LangRepo.findAll();
	}
	
	public Optional<Language> getById(int languageid) {
		return LangRepo.findById(languageid);
	}
	
	public Language create(Language language) {
		return LangRepo.save(language);
	}
	
	public Language update(Language language) {
		return LangRepo.save(language); 
	}
	
	public void delete(int languageid) {
		LangRepo.deleteById(languageid);
	}
	
}
