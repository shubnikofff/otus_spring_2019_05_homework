package ru.otus.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "lib_authors")
public class AuthorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
}
