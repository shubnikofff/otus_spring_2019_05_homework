package ru.otus.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "roles")
@Data
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@ManyToMany(mappedBy = "roles", targetEntity = User.class)
	private List<User> users;
}
