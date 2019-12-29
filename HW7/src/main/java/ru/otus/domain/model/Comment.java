package ru.otus.domain.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@Getter
@Builder
@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(
			name = "text",
			nullable = false
	)
	private String text;

	@ManyToOne(targetEntity = Book.class)
	@JoinColumn(name = "book_id")
	private Book book;
}
