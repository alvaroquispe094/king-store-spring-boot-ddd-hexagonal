package com.groupal.king.store.adapter.database.model;

import com.groupal.king.store.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(	name = "users", schema = "users",
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "user_name"),
			@UniqueConstraint(columnNames = "email")
		})
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstname;

	@Column(name = "last_name")
	private String lastname;

	@Column(name = "user_name")
	private String username;

	private String email;

	private String password;

	private String gender;

	@Column(name = "birth_date")
	private String birthDate;

	private String phone;

	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_roles", schema = "users",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleModel> roles = new HashSet<>();

	public User toDomain(){
		return User.builder()
				.id(id)
				.firstname(firstname)
				.lastname(lastname)
				.username(username)
				.email(email)
				.password(password)
				.gender(gender)
				.birthDate(birthDate)
				.phone(phone)
				.roles(toRoles(roles))
				.build();
	}

	public Set<String> toRoles(Set<RoleModel> roles) {
		Set<String> list = new HashSet<>();
		roles.forEach(role ->  list.add(role.getName().toString()) );
		return list;
	}

}
