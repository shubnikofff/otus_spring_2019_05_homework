package ru.otus.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lib_genres")
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
}
