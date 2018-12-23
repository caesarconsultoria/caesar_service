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
@Table(name = "sqmcaesarservice.tbl_status")
@SequenceGenerator(name = "seq_id_status", sequenceName = "sqmcaesarservice.seq_id_status")
@SuppressWarnings("serial")
public class Status implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_status")
	@Column(name = "id_status", nullable = false)
	private long idStatus;

	@Column(name = "status", nullable = false)
	private String descricaoStatus;

	public long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(long idStatus) {
		this.idStatus = idStatus;
	}

	public String getDescricaoStatus() {
		return descricaoStatus;
	}

	public void setDescricaoStatus(String descricaoStatus) {
		this.descricaoStatus = descricaoStatus;
	}

}
