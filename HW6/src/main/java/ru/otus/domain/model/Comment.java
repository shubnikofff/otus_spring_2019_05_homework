package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "text", nullable = false, length = 1024)
	private String text;

	@ManyToOne(
			cascade = CascadeType.ALL,
			targetEntity = Book.class,
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "book_id")
	private Book book;
}
