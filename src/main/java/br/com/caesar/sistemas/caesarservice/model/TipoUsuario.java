package br.com.caesar.sistemas.caesarservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sqmcaesarservice.tbl_tipo_usuario")
@SequenceGenerator(name = "seq_id_tipo_usuario", sequenceName = "sqmcaesarservice.seq_id_tipo_usuario")
@SuppressWarnings("serial")
public class TipoUsuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_tipo_usuario")
	@Column(name = "id_tipo_usuario", nullable = false)
	private long idTipoUsuario;

	@Column(name = "tipo_usuario", nullable = false)
	private String descricaoTipoUsuario;

	public long getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(long idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public String getDescricaoTipoUsuario() {
		return descricaoTipoUsuario;
	}

	public void setDescricaoTipoUsuario(String descricaoTipoUsuario) {
		this.descricaoTipoUsuario = descricaoTipoUsuario;
	}

}
