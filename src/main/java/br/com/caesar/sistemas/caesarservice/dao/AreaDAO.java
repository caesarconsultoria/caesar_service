package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caesar.sistemas.caesarservice.model.Area;

public class AreaDAO {

	EntityManager em;

	public AreaDAO(EntityManager em) {
		this.em = em;
	}

	public Area cadastrar(Area area) {
		em.getTransaction().begin();
		em.persist(area);
		em.getTransaction().commit();
		return area;
	}

	public List<Area> listar() {
		return em.createQuery("select a from Area a", Area.class).getResultList();
	}

	public Area localizarPorId(long idArea) {
		return em.find(Area.class, idArea);
	}

	public void atualizar(Area area) {
		em.getTransaction().begin();
		em.merge(area);
		em.getTransaction().commit();
	}

	public void remover(Area area) {
		em.getTransaction().begin();
		em.remove(area);
		em.getTransaction().commit();
	}

}
