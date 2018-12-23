package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caesar.sistemas.caesarservice.model.Uf;

public class UfDAO {

	EntityManager em;

	public UfDAO(EntityManager em) {
		this.em = em;
	}

	public Uf cadastrar(Uf uf) {
		em.getTransaction().begin();
		em.persist(uf);
		em.getTransaction().commit();
		return uf;
	}

	public List<Uf> listar() {
		return em.createQuery("select a from Uf a", Uf.class).getResultList();
	}

	public Uf localizarPorId(long idUf) {
		return em.find(Uf.class, idUf);
	}

	public void atualizar(Uf uf) {
		em.getTransaction().begin();
		em.merge(uf);
		em.getTransaction().commit();
	}

	public void remover(Uf uf) {
		em.getTransaction().begin();
		em.remove(uf);
		em.getTransaction().commit();
	}

}
