package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

import br.com.caesar.sistemas.caesarservice.model.Demanda;
import br.com.caesar.sistemas.caesarservice.model.StatusDemanda;

public class StatusDemandaDAO {

	private EntityManager em;

	public StatusDemandaDAO(EntityManager em) {
		this.em = em;
	}

	public StatusDemanda cadastrar(StatusDemanda statusDemanda) {
		em.getTransaction().begin();
		em.persist(statusDemanda);
		em.getTransaction().commit();
		return statusDemanda;
	}

	public List<StatusDemanda> listar() {
		return em.createQuery("select s from StatusDemanda s", StatusDemanda.class).getResultList();
	}

	public StatusDemanda localizarPorId(long id) {
		return em.find(StatusDemanda.class, id);
	}

	public void atualizar(StatusDemanda statusDemanda) {
		em.getTransaction().begin();
		em.merge(statusDemanda);
		em.getTransaction().commit();
	}

	public List<StatusDemanda> localizarPorSolucao(Demanda demanda) {
		try {
			return em.createQuery("select s from StatusDemanda s where s.solucao = :demanda", StatusDemanda.class)
					.setParameter("demanda", demanda).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void remover(StatusDemanda statusDemanda) {
		em.getTransaction().begin();
		em.remove(statusDemanda);
		em.getTransaction().commit();
	}

	public void removerPorDemanda(Demanda demanda) {
		try {
			List<StatusDemanda> rows = em
					.createQuery("select s from StatusDemanda s where s.demanda = :demanda", StatusDemanda.class)
					.setParameter("demanda", demanda).getResultList();
			if (rows != null) {
				em.getTransaction().begin();
				for (int i = 0; i < rows.size(); i++)
					em.remove(rows.get(i));
				em.getTransaction().commit();
			}
		} catch (NoResultException | RollbackException re) {
			System.out.println("Erro na remoção dos status para a demanda!");
		}
	}
}
