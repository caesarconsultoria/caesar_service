package br.com.caesar.sistemas.caesarservice.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "sqmcaesarservice.tbl_status_demanda")
@SequenceGenerator(name = "seq_id_status_demanda", sequenceName = "sqmcaesarservice.seq_id_status_demanda")
@SuppressWarnings("serial")
public class StatusDemanda implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_status_demanda")
	@Column(name = "id_status_demanda", nullable = false)
	private long idStatusDemanda;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_id_demanda"), name = "id_demanda", referencedColumnName = "id_demanda")
	private Demanda demanda;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_id_status"), name = "id_status", referencedColumnName = "id_status")
	private Status status;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_id_usuario"), name = "id_usuario", referencedColumnName = "id_usuario")
	private Usuario usuario;

	@Column(nullable = false)
	private Date data;

	public long getIdStatusDemanda() {
		return idStatusDemanda;
	}

	public void setIdStatusDemanda(long idStatusDemanda) {
		this.idStatusDemanda = idStatusDemanda;
	}

	public Demanda getDemanda() {
		return demanda;
	}

	public void setDemanda(Demanda demanda) {
		this.demanda = demanda;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
