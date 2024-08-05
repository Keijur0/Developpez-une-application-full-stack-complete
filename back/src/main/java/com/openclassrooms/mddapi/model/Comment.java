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
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a comment in the system.
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

	/**
	 * Unique identifier for the comment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	/**
	 * Author of the comment.
	 */
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "user_id")
	private User user;

	/**
	 * Post to which the comment belongs.
	 */
	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "post_id")
	private Post post;

	/**
	 * The message content of the comment.
	 */
	@NotBlank
	@NonNull
	@Size(max = 2000)
	private String message;

	/**
	 * Timestamp when the comment was created.
	 */
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
