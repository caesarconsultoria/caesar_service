package br.com.caesar.sistemas.caesarservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sqmcaesarservice.tbl_usuario")
@SequenceGenerator(name = "seq_id_usuario", sequenceName = "sqmcaesarservice.seq_id_usuario")
@SuppressWarnings("serial")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_usuario")
	@Column(name = "id_usuario", nullable = false)
	private long idUsuario;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_id_tipo_usuario"), name = "id_tipo_usuario", referencedColumnName = "id_tipo_usuario")
	// @Column(name = "id_tipo_usuario", nullable = false)
	private TipoUsuario tipoUsuario;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_id_uf"), name = "id_uf", referencedColumnName = "id_Uf")
	// @Column(name = "id_uf", nullable = false)
	private Uf uf;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "celular", nullable = false)
	private String celular;

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}
