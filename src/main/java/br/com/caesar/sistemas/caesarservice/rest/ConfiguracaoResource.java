package br.com.caesar.sistemas.caesarservice.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.caesar.sistemas.caesarservice.Application;
import br.com.caesar.sistemas.caesarservice.dao.ConfiguracaoDAO;
import br.com.caesar.sistemas.caesarservice.exception.AutenticacaoException;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.model.Configuracao;
import br.com.caesar.sistemas.caesarservice.util.AutenticacaoLDAP;
import br.com.caesar.sistemas.caesarservice.util.StringUtil;

@Path("configuracao")
public class ConfiguracaoResource {

	private static final Logger LOGGER = Logger.getLogger(Configuracao.class.getSimpleName());

	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listaUsuarios(@Context HttpServletRequest req) {
		// Usuario usuarioLogado = (Usuario)
		// req.getSession().getAttribute("usuario");
		// if (usuarioLogado == null) {
		// return Response.status(Status.UNAUTHORIZED).entity("Acesso
		// negado").build();
		// }

		EntityManager em = PersistenceManager.getEntityManager();
		try {
			List<Configuracao> configuracoes = new ConfiguracaoDAO(em).lista();
			return Response.ok(configuracoes).build();
		} finally {
			em.close();
		}
	}

	/**
	 * Consulta configuracao por chave
	 */
	@Path("{chave: [a-zA-Z\\._0-9]+}")
	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultaConfiguracao(@Context HttpServletRequest req, @PathParam("chave") String chave) {

		// Usuario usuarioLogado = (Usuario)
		// req.getSession().getAttribute("usuario");
		// if (usuarioLogado == null) {
		// return Response.status(Status.UNAUTHORIZED).entity("Acesso
		// negado").build();
		// }

		EntityManager em = PersistenceManager.getEntityManager();
		try {
			Configuracao conf = new ConfiguracaoDAO(em).localizaPorChave(chave);
			if (conf != null) {
				return Response.ok(conf).build();
			}
			return Response.status(Status.NOT_FOUND).entity("Configuração não localizada").build();
		} finally {
			em.close();
		}
	}

	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastra(@Context HttpServletRequest req, @FormParam("chave") String chave,
			@FormParam("valor") String valor, @FormParam("descricao") String descricao) {

		// Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");
		// if (usuario == null) {
		// return Response.status(Status.UNAUTHORIZED).entity("Acesso
		// negado").build();
		// }

		if (StringUtil.isEmpty(chave)) {
			return Response.status(Status.BAD_REQUEST).entity("Chave não informada").build();
		}

		if (StringUtil.isEmpty(valor)) {
			return Response.status(Status.BAD_REQUEST).entity("Valor não informado").build();
		}

		if (StringUtil.isEmpty(descricao)) {
			return Response.status(Status.BAD_REQUEST).entity("Descrição não informada").build();
		}

		EntityManager em = PersistenceManager.getEntityManager();
		ConfiguracaoDAO confDAO = new ConfiguracaoDAO(em);
		Configuracao c = confDAO.localizaPorChave(chave);
		if (c != null && c.getChave().equals(chave)) {
			return Response.status(Status.CONFLICT).entity("Chave já cadastrada").build();
		}

		try {
			Configuracao novaConf = new Configuracao();
			novaConf.setChave(chave);
			novaConf.setValor(valor);
			novaConf.setDescricao(descricao);

			confDAO.cadastra(novaConf);
			return Response.ok(novaConf).build();
		} catch (RollbackException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			return Response.status(Status.BAD_REQUEST)
					.entity("Verifique se a chave da configuração já foi cadastrada no sistema").build();
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			return Response.serverError().entity("Ocorreu um erro ao salvar os dados da configuração").build();
		} finally {
			em.close();
		}
	}

	@Path("{chave: [a-zA-Z0-9_.]*}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizaConfiguracao(@Context HttpServletRequest req, @FormParam("chave") String chave,
			@FormParam("chaveOld") String chaveOld, @FormParam("valor") String valor,
			@FormParam("descricao") String descricao) {

		// Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");
		// if (usuario == null) {
		// return Response.status(Status.UNAUTHORIZED).entity("Acesso
		// negado").build();
		// }

		if (StringUtil.isEmpty(chave)) {
			return Response.status(Status.BAD_REQUEST).entity("Chave não informada").build();
		}

		if (StringUtil.isEmpty(valor)) {
			return Response.status(Status.BAD_REQUEST).entity("Valor não informado").build();
		}

		if (StringUtil.isEmpty(descricao)) {
			return Response.status(Status.BAD_REQUEST).entity("Descrição não informada").build();
		}

		EntityManager em = PersistenceManager.getEntityManager();
		try {
			ConfiguracaoDAO confDao = new ConfiguracaoDAO(em);
			Configuracao confBean = confDao.localizaPorChave(chave);

			if (!chaveOld.equals(chave)) {
				Configuracao outraConf = confDao.localizaPorChave(chave);
				if (outraConf != null && outraConf.getChave().equals(chave)) {
					return Response.status(Status.BAD_REQUEST).entity("Chave já cadastrada no sistema").build();
				}
			}
			confBean.setChave(chave);
			confBean.setValor(valor);
			confBean.setDescricao(descricao);

			confDao.atualiza(confBean);
			return Response.ok(confBean).build();
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			return Response.serverError().entity("Ocorreu um erro ao salvar os dados do configuração").build();
		} finally {
			em.close();
		}
	}

	@Path("/ldap")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizaConfiguracao(@Context HttpServletRequest req, @FormParam("url") String url,
			@FormParam("user") String user, @FormParam("password") String password) {

		// Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");
		// if (usuario == null) {
		// return Response.status(Status.UNAUTHORIZED).entity("Acesso
		// negado").build();
		// }

		if (StringUtil.isEmpty(url)) {
			return Response.status(Status.BAD_REQUEST).entity("URL não informada").build();
		}

		if (StringUtil.isEmpty(user)) {
			return Response.status(Status.BAD_REQUEST).entity("Usuário não informado").build();
		}

		if (StringUtil.isEmpty(password)) {
			return Response.status(Status.BAD_REQUEST).entity("Senha não informada").build();
		}

		try {
			if (AutenticacaoLDAP.testarAutenticacao(url, user, password)) {
				return Response.ok("Conexão com LDAP bem sucedida").build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity("Erro ao conectar com LDAP").build();
			}
		} catch (AutenticacaoException e) {
			String errorMesage = e.getMotivo();
			return Response.status(Status.BAD_REQUEST).entity("Erro ao conectar com LDAP\n" + errorMesage).build();
		}
	}

}
