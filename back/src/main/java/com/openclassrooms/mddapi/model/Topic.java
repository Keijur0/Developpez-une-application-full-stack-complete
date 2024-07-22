package com.openclassrooms.mddapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Represents a topic in the system.
 */
@Data
@Entity
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
	@Column(nullable = false)
	private String name;
	
}
