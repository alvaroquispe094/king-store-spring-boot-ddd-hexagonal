/*package com.groupal.king.store.adapter.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_roles", schema = "users")
public class UserRolesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)  // one to one
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserModel user;

	@ManyToOne(fetch = FetchType.EAGER)  // one to one
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	private RoleModel role;

}

 */