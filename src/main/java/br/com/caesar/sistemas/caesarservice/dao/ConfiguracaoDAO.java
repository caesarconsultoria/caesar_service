package br.com.caesar.sistemas.caesarservice.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.caesar.sistemas.caesarservice.model.Configuracao;

public class ConfiguracaoDAO {

	private static final Logger LOGGER = Logger.getLogger(ConfiguracaoDAO.class.getSimpleName());
	private EntityManager entityManager;

	public ConfiguracaoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Configuracao cadastra(Configuracao conf) {
		entityManager.getTransaction().begin();
		entityManager.persist(conf);
		entityManager.getTransaction().commit();
		return conf;
	}

	public Configuracao localizaPorChave(String chave) {
		try {
			return entityManager.createQuery("select u from Configuracao u where lower(u.chave) = lower(:chave)",
					Configuracao.class).setParameter("chave", chave).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Configuracao> lista() {
		return entityManager.createQuery("select u from Configuracao u order by u.chave", Configuracao.class)
				.getResultList();
	}

	public void atualiza(Configuracao conf) {
		entityManager.getTransaction().begin();
		entityManager.merge(conf);
		entityManager.getTransaction().commit();
	}

	public String getAsString(String chave) {
		Configuracao config = localizaPorChave(chave);
		if (config != null) {
			return config.getValor();
		}
		return null;
	}

	public long getAsLong(String chave, long valorPadrao) {
		Configuracao config = localizaPorChave(chave);
		try {
			return Long.parseLong(config.getValor());
		} catch (NullPointerException | NumberFormatException e) {
			LOGGER.info(String.format("chave %s não localizada, usando valor padrão (%d)", chave, valorPadrao));
			return valorPadrao;
		}
	}

	public boolean getAsBoolean(String chave) {
		Configuracao config = localizaPorChave(chave);
		try {
			return "true".equalsIgnoreCase(config.getValor()) || Long.parseLong(config.getValor()) == 1;
		} catch (NullPointerException | NumberFormatException e) {
			return false;
		}
	}
}
