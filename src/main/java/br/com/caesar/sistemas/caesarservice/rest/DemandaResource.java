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
import br.com.caesar.sistemas.caesarservice.dao.DemandaDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.model.Area;
import br.com.caesar.sistemas.caesarservice.model.Demanda;

@Path("/demanda")
public class DemandaResource {

	/**
	 * Serviço utilizado para listar todas as Demandas cadastradas na base
	 * 
	 * @return
	 * @author Julio Cesar - 26/12/2018
	 */
	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listar() {

		EntityManager em = PersistenceManager.getEntityManager();
		DemandaDAO dao = new DemandaDAO(em);
		try {
			List<Demanda> demanda = dao.listar();
			return Response.ok().entity(demanda).build();
		} catch (RollbackException re) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Não foi possível listar as Demandas cadastradas. Favor Verificar.")
					.build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para cadastrar uma nova demanda
	 * 
	 * @param idArea
	 * @return
	 * @author Julio Cesar - 26/12/2018
	 */
	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastrar(@FormParam("idArea") long idArea) {

		EntityManager em = PersistenceManager.getEntityManager();
		DemandaDAO dao = new DemandaDAO(em);
		Demanda demanda = new Demanda();
		AreaDAO areaDAO = new AreaDAO(em);

		try {

			// Localizando a Área pelo id informado
			Area area = new Area();
			area = areaDAO.localizarPorId(idArea);
			demanda.setArea(area);

			dao.cadastrar(demanda);
			return Response.ok().entity(demanda).build();

		} catch (RollbackException re) {
			return Response.ok()
					.entity("Ocorreu um erro e a Demanda não foi cadastrada.")
					.build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para localizar uma Demanda dado seu id
	 * 
	 * @param req
	 * @param idDemanda
	 * @return
	 * @author Julio Cesar - 26/12/2018
	 */
	@GET
	@Path("/{idDemanda: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultar(@Context HttpServletRequest req,
			@PathParam("idDemanda") long idDemanda) {

		EntityManager em = PersistenceManager.getEntityManager();
		DemandaDAO dao = new DemandaDAO(em);
		Demanda demanda = new Demanda();
		try {
			demanda = dao.localizarPorId(idDemanda);
			if (demanda != null) {
				return Response.ok(demanda).build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity("Demanda não localizada").build();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para atualizar uma Demanda cadastrada anteriormente
	 * 
	 * @param idDemanda
	 * @param idArea
	 * @return
	 * @author Julio Cesar - 26/12/2018
	 */
	@POST
	@Path("{idDemanda: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response atualizar(@PathParam("idDemanda") long idDemanda,
			@PathParam("idArea") long idArea) {

		EntityManager em = PersistenceManager.getEntityManager();
		DemandaDAO demandaDAO = new DemandaDAO(em);
		AreaDAO areaDAO = new AreaDAO(em);

		Demanda demanda = new Demanda();
		Area area = new Area();

		try {

			demanda = demandaDAO.localizarPorId(idDemanda);
			area = areaDAO.localizarPorId(idArea);
			demanda.setArea(area);
			return Response.ok(demanda).build();

		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para excluir uma determinada Demanda dado seu Id
	 * 
	 * @param req
	 * @param idDemanda
	 * @return
	 * @author Julio Cesar - 26/12/2018
	 */
	@DELETE
	@Path("/{idDemanda: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response remover(@Context HttpServletRequest req,
			@PathParam("idDemanda") long idDemanda) {

		EntityManager em = PersistenceManager.getEntityManager();
		DemandaDAO dao = new DemandaDAO(em);
		Demanda demanda = new Demanda();
		try {
			demanda = dao.localizarPorId(idDemanda);
			if (demanda != null) {
				dao.remover(demanda);
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Não foi possível excluir a Demanda informada")
					.build();
		} finally {
			em.close();
		}
	}

}
