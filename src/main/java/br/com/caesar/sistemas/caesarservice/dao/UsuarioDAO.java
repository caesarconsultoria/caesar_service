package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caesar.sistemas.caesarservice.model.Usuario;

public class UsuarioDAO {

	EntityManager em;

	public UsuarioDAO(EntityManager em) {
		this.em = em;
	}

	public Usuario cadastrar(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		return usuario;
	}

	public List<Usuario> listar() {
		return em.createQuery("select a from Usuario a", Usuario.class).getResultList();
	}

	public Usuario localizarPorId(long idUsuario) {
		return em.find(Usuario.class, idUsuario);
	}

	public void atualizar(Usuario usuario) {
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
	}

	public void remover(Usuario usuario) {
		em.getTransaction().begin();
		em.remove(usuario);
		em.getTransaction().commit();
	}

}
