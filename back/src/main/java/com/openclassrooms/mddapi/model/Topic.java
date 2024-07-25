package com.openclassrooms.mddapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a topic in the system.
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "topics")
public class Topic {

	/**
	 * Topic's unique id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_id")
	private Long id;
	
	/**
	 * Topic's name.
	 */
	@NonNull
	@Column(nullable = false)
	@NotBlank
	private String name;

	/**
	 * Topic's description
	 */
	@NonNull
	@Column(nullable = false)
	@NotBlank
	private String description;
}
