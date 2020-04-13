package ru.otus.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lib_comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "text", nullable = false)
	private String text;

	@ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;
}
