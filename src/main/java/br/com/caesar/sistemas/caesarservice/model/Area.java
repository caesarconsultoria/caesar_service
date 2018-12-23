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
@Table(name = "sqmcaesarservice.tbl_area")
@SequenceGenerator(name = "seq_id_area", sequenceName = "sqmcaesarservice.seq_id_area")
@SuppressWarnings("serial")
public class Area implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_area")
	@Column(name = "id_area", nullable = false)
	private long idArea;

	@Column(name = "area", nullable = false)
	private String descricaoArea;

	public long getIdArea() {
		return idArea;
	}

	public void setIdArea(long idArea) {
		this.idArea = idArea;
	}

	public String getDescricaoArea() {
		return descricaoArea;
	}

	public void setDescricaoArea(String descricaoArea) {
		this.descricaoArea = descricaoArea;
	}

}
