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
@Table(name = "sqmcaesarservice.tbl_uf")
@SequenceGenerator(name = "seq_id_uf", sequenceName = "sqmcaesarservice.seq_id_uf")
@SuppressWarnings("serial")
public class Uf implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_uf")
	@Column(name = "id_uf", nullable = false)
	private long idUf;

	@Column(name = "uf", nullable = false)
	private String uf;

	@Column(name = "estado", nullable = false)
	private String descricaoEstado;

	public long getIdUf() {
		return idUf;
	}

	public void setIdUf(long idUf) {
		this.idUf = idUf;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getDescricaoEstado() {
		return descricaoEstado;
	}

	public void setDescricaoEstado(String descricaoEstado) {
		this.descricaoEstado = descricaoEstado;
	}

}
