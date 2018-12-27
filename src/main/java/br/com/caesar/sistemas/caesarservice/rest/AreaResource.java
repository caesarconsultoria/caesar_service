package br.com.caesar.sistemas.caesarservice.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.caesar.sistemas.caesarservice.Application;
import br.com.caesar.sistemas.caesarservice.dao.AreaDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.model.Area;
import br.com.caesar.sistemas.caesarservice.util.StringUtil;

@Path("/area")
public class AreaResource {

	/**
	 * Serviço utilizado para listar todas as Áreas de atuação existentes na
	 * base
	 * 
	 * @return
	 * @author Julio Cesar - 22/12/2018
	 */
	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listar() {

		EntityManager em = PersistenceManager.getEntityManager();
		AreaDAO dao = new AreaDAO(em);
		try {
			List<Area> areas = dao.listar();
			return Response.ok().entity(areas).build();
		} catch (RollbackException re) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Nao pegou. Verificar.").build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para cadastrar uma nova área de atuação
	 * 
	 * @param req
	 * @param descricaoArea
	 * @return mensagem de sucesso
	 * @author Julio Cesar 22/12/2018
	 */
	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastrar(@Context HttpServletRequest req,
			@FormParam("descricaoArea") String descricaoArea) {

		EntityManager em = PersistenceManager.getEntityManager();
		if (StringUtil.isEmpty(descricaoArea))
			return Response.status(Status.BAD_REQUEST)
					.entity("Nome da area não informado").build();
		AreaDAO dao = new AreaDAO(em);
		Area novaArea = new Area();
		try {
			novaArea.setDescricaoArea(descricaoArea);
			dao.cadastrar(novaArea);
			return Response.ok().entity(novaArea).build();
		} catch (RollbackException re) {
			return Response.ok().entity("Area ja cadastrada no banco de dados.")
					.build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para localizar uma área de atuação por Id
	 * 
	 * @param req
	 * @param idArea
	 * @return uma área encontrada ou mensagem de área não encontrada
	 * @author Julio Cesar 22/12/2018
	 */
	@GET
	@Path("/{idArea: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultar(@Context HttpServletRequest req,
			@PathParam("idArea") long idArea) {

		EntityManager em = PersistenceManager.getEntityManager();
		AreaDAO dao = new AreaDAO(em);
		Area area = new Area();
		try {
			area = dao.localizarPorId(idArea);
			if (area != null) {
				return Response.ok(area).build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity("Area não localizada").build();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para cadastrar uma nova Área de atuação
	 * 
	 * @param req
	 * @param id
	 * @param descricaoArea
	 * @return
	 * @author Julio Cesar - 22/12/2018
	 */
	@POST
	// @Path("{idArea: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response atualizar(@FormParam("idArea") long idArea,
			@FormParam("descricaoArea") String descricaoArea) {

		if (StringUtil.isEmpty(descricaoArea))
			return Response.status(Status.BAD_REQUEST)
					.entity("Área de atuação não informada").build();

		EntityManager em = PersistenceManager.getEntityManager();
		AreaDAO dao = new AreaDAO(em);
		Area area = new Area();
		try {
			area = dao.localizarPorId(idArea);
			area.setDescricaoArea(descricaoArea);
			dao.atualizar(area);
			return Response.ok(area).build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para excluir uma Área de atuação
	 * 
	 * @param req
	 * @param idArea
	 * @return
	 * @author Julio Cesar - 22/12/2018
	 */
	@DELETE
	@Path("/{idArea: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response remover(@Context HttpServletRequest req,
			@PathParam("idArea") long idArea) {

		EntityManager em = PersistenceManager.getEntityManager();
		AreaDAO dao = new AreaDAO(em);
		Area area = new Area();
		try {
			area = dao.localizarPorId(idArea);
			if (area != null) {
				dao.remover(area);
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Não foi possível excluir area").build();
		} finally {
			em.close();
		}
	}
}
