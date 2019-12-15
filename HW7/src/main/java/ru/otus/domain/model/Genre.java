package ru.otus.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@Getter
@Builder
@Entity
@Table(name = "genres")
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			name = "name",
			unique = true,
			nullable = false
	)
	private String name;

	@OneToMany(
			targetEntity = Book.class,
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "id")
	private List<Book> books;
}
