package com.lead.CatalagoFilmes.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Usuario implements UserDetails {

	public Usuario(Long id, String nome, String cpf, String telefone, String email, String senha, Idioma idioma) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.idioma = idioma;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank
	@NotEmpty
	private String nome;

	@Column(nullable = false)
	@NotBlank
	@NotEmpty
	private String cpf;

	@Column(nullable = false)
	@NotBlank
	@NotEmpty
	private String telefone;

	@Column(nullable = false)
	@NotBlank
	@NotEmpty
	private String email;

	@Column(nullable = false)
	@NotBlank
	@NotEmpty
	private String senha;

	@OneToOne
	private Idioma idioma;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Perfil> perfis = new ArrayList<>();

	private String token;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perfis;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}