package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caesar.sistemas.caesarservice.model.Demanda;

public class DemandaDAO {

	EntityManager em;

	public DemandaDAO(EntityManager em) {
		this.em = em;
	}

	public Demanda cadastrar(Demanda demanda) {
		em.getTransaction().begin();
		em.persist(demanda);
		em.getTransaction().commit();
		return demanda;
	}

	public List<Demanda> listar() {
		return em.createQuery("select a from Demanda a", Demanda.class).getResultList();
	}

	public Demanda localizarPorId(long idDemanda) {
		return em.find(Demanda.class, idDemanda);
	}

	public void atualizar(Demanda demanda) {
		em.getTransaction().begin();
		em.merge(demanda);
		em.getTransaction().commit();
	}

	public void remover(Demanda demanda) {
		em.getTransaction().begin();
		em.remove(demanda);
		em.getTransaction().commit();
	}

}
