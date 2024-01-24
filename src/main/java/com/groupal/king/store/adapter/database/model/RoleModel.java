package com.groupal.king.store.adapter.database.model;


import com.groupal.king.store.domain.enums.ERole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles", schema = "users")
public class RoleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 40)
	private ERole name;

	public RoleModel() {

	}

	public RoleModel(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}