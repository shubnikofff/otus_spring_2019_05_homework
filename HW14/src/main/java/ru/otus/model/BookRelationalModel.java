package ru.otus.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "lib_books")
public class BookRelationalModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false, unique = true)
	private String title;

	@ManyToOne(targetEntity = GenreRelationalModel.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "genre_id", nullable = false)
	private GenreRelationalModel genre;

	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(targetEntity = AuthorRelationalModel.class,  fetch = FetchType.EAGER)
	@JoinTable(
			name = "lib_books_authors",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id")
	)
	private Collection<AuthorRelationalModel> authors;
}
