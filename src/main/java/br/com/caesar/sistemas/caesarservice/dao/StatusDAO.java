package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caesar.sistemas.caesarservice.model.Status;

public class StatusDAO {

	EntityManager em;

	public StatusDAO(EntityManager em) {
		this.em = em;
	}

	public Status cadastrar(Status status) {
		em.getTransaction().begin();
		em.persist(status);
		em.getTransaction().commit();
		return status;
	}

	public List<Status> listar() {
		return em.createQuery("select s from Status s", Status.class).getResultList();
	}

	public Status localizarPorId(long id) {
		return em.find(Status.class, id);
	}

	public void atualizar(Status status) {
		em.getTransaction().begin();
		em.merge(status);
		em.getTransaction().commit();
	}

	public void remover(Status status) {
		em.getTransaction().begin();
		em.remove(status);
		em.getTransaction().commit();
	}

}
