package com.revature.dungeonsite.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Language {

	@Id
	@Getter  @Setter
	private long languageid;
	
	@Column(nullable=false, unique=true)
	@Getter  @Setter
	private String language;
}
