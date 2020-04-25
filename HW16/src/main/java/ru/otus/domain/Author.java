package ru.otus.domain;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "lib_authors")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(
			targetEntity = Book.class,
			cascade = CascadeType.MERGE
	)
	@JoinTable(
			name = "lib_books_authors",
			joinColumns = @JoinColumn(name = "author_id"),
			inverseJoinColumns = @JoinColumn(name = "book_id")
	)
	private List<Book> books;
}
