package com.revature.dungeonsite.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dungeonsite.models.Language;
import com.revature.dungeonsite.repositories.LanguageRepository;


@Service
public class LanguageService {
	private final LanguageRepository LangRepo;

	public LanguageService(LanguageRepository LangRepo) {
		this.LangRepo = LangRepo;
	}

	public List<Language> getAll(){
		return LangRepo.findAll();
	}
	
	public Optional<Language> getById(long languageid) {
		return LangRepo.findById(languageid);
	}
	
	public Language create(Language language) {
		return LangRepo.save(language);
	}
	
	public Language update(Language language) {
		return LangRepo.save(language); 
	}
	
	public void delete(long languageid) {
		LangRepo.deleteById(languageid);
	}
	
}
