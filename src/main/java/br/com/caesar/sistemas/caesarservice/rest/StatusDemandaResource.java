package br.com.caesar.sistemas.caesarservice.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.caesar.sistemas.caesarservice.Application;
import br.com.caesar.sistemas.caesarservice.dao.DemandaDAO;
import br.com.caesar.sistemas.caesarservice.dao.StatusDAO;
import br.com.caesar.sistemas.caesarservice.dao.StatusDemandaDAO;
import br.com.caesar.sistemas.caesarservice.dao.UsuarioDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.model.Demanda;
import br.com.caesar.sistemas.caesarservice.model.StatusDemanda;
import br.com.caesar.sistemas.caesarservice.model.Usuario;

@Path("/statusdemanda")
public class StatusDemandaResource {

	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listar() {

		EntityManager em = PersistenceManager.getEntityManager();
		try {
			List<StatusDemanda> statusDemanda = new StatusDemandaDAO(em)
					.listar();
			return Response.ok(statusDemanda).build();
		} finally {
			em.close();
		}
	}

	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastrar(@FormParam("idDemanda") long idDemanda,
			@FormParam("idUsuario") long idUsuario,
			@FormParam("idStatus") long idStatus) {

		Usuario usuario = new Usuario();

		/* Encontra status dado o ID */
		EntityManager em = PersistenceManager.getEntityManager();
		br.com.caesar.sistemas.caesarservice.model.Status status = new br.com.caesar.sistemas.caesarservice.model.Status();
		StatusDAO statusDAO = new StatusDAO(em);
		try {
			status = statusDAO.localizarPorId(idStatus);
		} catch (IllegalArgumentException | NullPointerException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Status inválido.").build();
		}

		/* Encontra demanda dado o ID */
		Demanda demanda = new Demanda();
		DemandaDAO demandaDAO = new DemandaDAO(em);
		try {
			demanda = demandaDAO.localizarPorId(idDemanda);
		} catch (IllegalArgumentException | NullPointerException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Solução inválida.").build();
		}

		// Quando a Demanda for NOVA não deve conter nenhum id de usuário.
		// por isso antes de localizar um usuário, verifica-se se o idUsuario é
		// maior que 0 para garantir que um id foi informado e a pesquisa poder
		// ser realizada com sucesso

		/* Encontra usuário dado o ID */
		if (idUsuario > 0) {
			UsuarioDAO usuarioDAO = new UsuarioDAO(em);
			try {
				usuario = usuarioDAO.localizarPorId(idUsuario);
			} catch (IllegalArgumentException | NullPointerException e) {
				return Response.status(Status.BAD_REQUEST)
						.entity("Usuario não encotrado.").build();
			}
		}

		/* Cadastra nova relação */
		try {
			StatusDemanda statusDemanda = new StatusDemanda();
			StatusDemandaDAO statusDemandaDAO = new StatusDemandaDAO(em);
			statusDemanda.setStatus(status);
			statusDemanda.setDemanda(demanda);
			if (usuario.getIdUsuario() > 0) {
				statusDemanda.setUsuario(usuario);
			}
			// statusDemanda.setData(data);
			statusDemanda.setData(new Date());
			statusDemandaDAO.cadastrar(statusDemanda);
			return Response.ok(statusDemanda).build();
		} catch (RollbackException re) {
			return Response.ok().entity("Erro ao cadatrar uma nova demanda.")
					.build();
		} finally {
			em.close();
		}
	}

	@GET
	@Path("/{id: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultar(@PathParam("id") long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		Demanda demanda = new Demanda();
		DemandaDAO demandaDAO = new DemandaDAO(em);
		StatusDemandaDAO statusDemandaDAO = new StatusDemandaDAO(em);
		try {
			demanda = demandaDAO.localizarPorId(id);
			List<StatusDemanda> statusDemanda = statusDemandaDAO
					.localizarPorSolucao(demanda);
			if (statusDemanda != null) {
				return Response.ok(statusDemanda).build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity("Relação não existe.").build();
			}
		} finally {
			em.close();
		}
	}

	@POST
	// @Path("{idStatusDemanda: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response atualizar(
			@FormParam("idStatusDemanda") long idStatusDemanda,
			@FormParam("idStatus") long idStatus,
			@FormParam("idDemanda") long idDemanda,
			@FormParam("idUsuario") long idUsuario) {

		Usuario usuario = new Usuario();

		/* Encontra status dado o ID */
		EntityManager em = PersistenceManager.getEntityManager();
		br.com.caesar.sistemas.caesarservice.model.Status status = new br.com.caesar.sistemas.caesarservice.model.Status();
		StatusDAO statusDAO = new StatusDAO(em);
		try {
			status = statusDAO.localizarPorId(idStatus);
		} catch (IllegalArgumentException | NullPointerException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Status inválido.").build();
		}

		/* Encontra a demanda dado o ID */
		Demanda demanda = new Demanda();
		DemandaDAO demandaDAO = new DemandaDAO(em);
		try {
			demanda = demandaDAO.localizarPorId(idDemanda);
		} catch (IllegalArgumentException | NullPointerException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Solução inválida.").build();
		}

		/* Encontra usuário dado o ID */
		if (idUsuario > 0) {
			UsuarioDAO usuarioDAO = new UsuarioDAO(em);
			try {
				usuario = usuarioDAO.localizarPorId(idUsuario);
			} catch (IllegalArgumentException | NullPointerException e) {
				return Response.status(Status.BAD_REQUEST)
						.entity("Usuario não encotrado.").build();
			}
		}

		/* Atualiza relação */
		StatusDemanda statusDemanda = new StatusDemanda();
		StatusDemandaDAO statusDemandaDAO = new StatusDemandaDAO(em);
		try {
			statusDemanda = statusDemandaDAO.localizarPorId(idStatusDemanda);
			statusDemanda.setStatus(status);
			statusDemanda.setDemanda(demanda);
			if (usuario != null) {
				statusDemanda.setUsuario(usuario);
			}
			statusDemanda.setData(new Date());
			statusDemandaDAO.atualizar(statusDemanda);
			return Response.ok(statusDemanda).build();
		} catch (RollbackException re) {
			return Response.ok().entity("Problemas na atualização da demanda.")
					.build();
		} finally {
			em.close();
		}
	}

	@DELETE
	@Path("/{id: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response remover(@PathParam("id") long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		StatusDemanda statusDemanda = new StatusDemanda();
		StatusDemandaDAO statusDemandaDAO = new StatusDemandaDAO(em);
		try {
			statusDemanda = statusDemandaDAO.localizarPorId(id);
			if (statusDemanda != null) {
				statusDemandaDAO.remover(statusDemanda);
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Não foi possível excluir a demanda.").build();
		} finally {
			em.close();
		}
	}

}
