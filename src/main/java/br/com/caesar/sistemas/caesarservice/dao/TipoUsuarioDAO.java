package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caesar.sistemas.caesarservice.model.TipoUsuario;

public class TipoUsuarioDAO {

	EntityManager em;

	public TipoUsuarioDAO(EntityManager em) {
		this.em = em;
	}

	public TipoUsuario cadastrar(TipoUsuario tipoUsuario) {
		em.getTransaction().begin();
		em.persist(tipoUsuario);
		em.getTransaction().commit();
		return tipoUsuario;
	}

	public List<TipoUsuario> listar() {
		return em.createQuery("select a from TipoUsuario a", TipoUsuario.class).getResultList();
	}

	public TipoUsuario localizarPorId(long idTipoUsuario) {
		return em.find(TipoUsuario.class, idTipoUsuario);
	}

	public void atualizar(TipoUsuario tipoUsuario) {
		em.getTransaction().begin();
		em.merge(tipoUsuario);
		em.getTransaction().commit();
	}

	public void remover(TipoUsuario tipoUsuario) {
		em.getTransaction().begin();
		em.remove(tipoUsuario);
		em.getTransaction().commit();
	}

}
