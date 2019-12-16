package ru.otus.domain.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@Getter
@Builder
@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@ManyToOne(
			targetEntity = Genre.class,
			fetch = FetchType.EAGER
	)
	@JoinColumn(name = "genre_id")
	private Genre genre;

	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(
			targetEntity = Author.class,
			fetch = FetchType.EAGER
	)
	@JoinTable(
			name = "books_authors",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id")
	)
	private List<Author> authors;

	@OneToMany(
			targetEntity = Comment.class,
			cascade = CascadeType.REMOVE,
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "book_id")
	private List<Comment> comments;
}
