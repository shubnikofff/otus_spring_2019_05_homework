package ru.otus.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
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
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
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

	public static class Builder {
		private final String title;
		private final Genre genre;

		private Long id = null;
		private List<Author> authors = Collections.emptyList();
		private List<Comment> comments = Collections.emptyList();

		public Builder(String title, Genre genre) {
			this.title = title;
			this.genre = genre;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withAuthors(List<Author> authors) {
			this.authors = authors;
			return this;
		}

		public Builder withComments(List<Comment> comments) {
			this.comments = comments;
			return this;
		}

		public Book build() {
			return new Book(this);
		}
	}

	private Book(Builder builder) {
		id = builder.id;
		title = builder.title;
		genre = builder.genre;
		authors = builder.authors;
		comments = builder.comments;
	}
}
