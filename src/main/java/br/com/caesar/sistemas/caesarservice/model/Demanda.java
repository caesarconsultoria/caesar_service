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
@Table(name = "sqmcaesarservice.tbl_demanda")
@SequenceGenerator(name = "seq_id_demanda", sequenceName = "sqmcaesarservice.seq_id_demanda")
@SuppressWarnings("serial")
public class Demanda implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_demanda")
	@Column(name = "id_demanda", nullable = false)
	private long idDemanda;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_id_area"), name = "id_area", referencedColumnName = "id_area")
	private Area area;

	public long getIdDemanda() {
		return idDemanda;
	}

	public void setIdDemanda(long idDemanda) {
		this.idDemanda = idDemanda;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}
