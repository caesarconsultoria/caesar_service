package br.com.caesar.sistemas.caesarservice.rest;

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
import br.com.caesar.sistemas.caesarservice.dao.StatusDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.util.StringUtil;

@Path("/status")
public class StatusResource {

	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastrar(
			@FormParam("descricaoStatus") String descricaoStatus) {

		if (StringUtil.isEmpty(descricaoStatus))
			return Response.status(Status.BAD_REQUEST)
					.entity("Descição de status vazia.").build();

		EntityManager em = PersistenceManager.getEntityManager();
		StatusDAO dao = new StatusDAO(em);
		br.com.caesar.sistemas.caesarservice.model.Status novoStatus = new br.com.caesar.sistemas.caesarservice.model.Status();
		try {
			novoStatus.setDescricaoStatus(descricaoStatus);
			dao.cadastrar(novoStatus);
			return Response.ok().entity(novoStatus).build();
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Problema no cadastro de novo status.").build();
		} finally {
			em.close();
		}
	}

	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listar() {
		EntityManager em = PersistenceManager.getEntityManager();
		StatusDAO dao = new StatusDAO(em);
		try {

			List<br.com.caesar.sistemas.caesarservice.model.Status> status = dao
					.listar();
			return Response.ok().entity(status).build();
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Problema na listagem de status").build();
		} finally {
			em.close();
		}
	}

	@GET
	@Path("/{id: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultar(@PathParam("id") long id) {

		EntityManager em = PersistenceManager.getEntityManager();
		StatusDAO dao = new StatusDAO(em);
		br.com.caesar.sistemas.caesarservice.model.Status status = new br.com.caesar.sistemas.caesarservice.model.Status();
		try {
			status = dao.localizarPorId(id);
			if (status != null) {
				return Response.ok(status).build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity("Status não localizado").build();
			}
		} finally {
			em.close();
		}
	}

	@POST
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response atualizar(@FormParam("idStatus") long idStatus,
			@FormParam("descricaoStatus") String descricaoStatus) {

		if (StringUtil.isEmpty(descricaoStatus))
			return Response.status(Status.BAD_REQUEST)
					.entity("Descrição não informada").build();

		EntityManager em = PersistenceManager.getEntityManager();
		StatusDAO dao = new StatusDAO(em);
		br.com.caesar.sistemas.caesarservice.model.Status status = new br.com.caesar.sistemas.caesarservice.model.Status();
		try {
			status = dao.localizarPorId(idStatus);
			status.setDescricaoStatus(descricaoStatus);
			dao.atualizar(status);
			return Response.ok(status).build();
		} finally {
			em.close();
		}
	}

	@DELETE
	@Path("/{idStatus: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response remover(@PathParam("idStatus") long idStatus) {

		EntityManager em = PersistenceManager.getEntityManager();
		StatusDAO dao = new StatusDAO(em);
		br.com.caesar.sistemas.caesarservice.model.Status status = new br.com.caesar.sistemas.caesarservice.model.Status();
		try {
			status = dao.localizarPorId(idStatus);
			if (status != null) {
				dao.remover(status);
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Não foi possível excluir status").build();
		} finally {
			em.close();
		}
	}

}
