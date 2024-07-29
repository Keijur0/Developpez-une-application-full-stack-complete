package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a comment in the system
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	/**
	 * Comment's author
	 */
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "user_id")
	private User user;

	/**
	 * Comment's post
	 */
	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "post_id")
	private Post post;

	/**
	 * Comment's message
	 */
	@NotBlank
	@NonNull
	private String message;

	/**
	 * Comment's creation date
	 */
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
