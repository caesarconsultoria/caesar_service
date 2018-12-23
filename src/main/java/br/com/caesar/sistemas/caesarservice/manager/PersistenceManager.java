package br.com.caesar.sistemas.caesarservice.manager;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.com.caesar.sistemas.caesarservice.util.Config;

@WebListener
public class PersistenceManager implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(PersistenceManager.class.getSimpleName());
	private static EntityManagerFactory factory;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		factory = Persistence.createEntityManagerFactory("main");
		sce.getServletContext().setInitParameter("VERSAO", Config.get("sistema.versao"));
		sce.getServletContext().setInitParameter("ULTIMA_ATUALIZACAO", Config.get("sistema.data_atualizacao"));
		LOGGER.info("entityManagerFactory criado.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (factory != null && factory.isOpen()) {
			factory.close();
			LOGGER.info("entityManagerFactory encerrado.");
		}
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
