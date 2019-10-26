package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "title", unique = true, nullable = false)
	private String title;

	@ManyToOne(
			targetEntity = Genre.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
	)
	@JoinColumn(name = "genre_id")
	private Genre genre;

	@ManyToMany(
			targetEntity = Author.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	@JoinTable(
			name = "books_authors",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id")
	)
	private List<Author> authors;

	@OneToMany(
			targetEntity = Comment.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "book_id")
	private List<Comment> comments;
}