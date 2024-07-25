package com.openclassrooms.mddapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a post in the system
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post {

	/**
	 * Post's unique id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="post_id")
	private Long id;
	
	/**
	 * Post's topic
	 */
	@ManyToOne
	@JoinColumn(name = "topic_id", referencedColumnName = "topic_id")
	private Topic topic;

	/**
	 * Post's title
	 */
	@NotNull
	@NotBlank
	private String title;

	/**
	 * Post's author
	 */
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "user_id")
	private User user;

	/**
	 * Post's content
	 */
	@NotNull
	@NotBlank
	private String content;

	/**
	 * Post's creation date
	 */
	@CreatedDate
	@Column(name = "created_at")
	private Date createdAt;
}
