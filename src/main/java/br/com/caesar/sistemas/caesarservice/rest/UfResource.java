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
import br.com.caesar.sistemas.caesarservice.dao.UfDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.model.Uf;
import br.com.caesar.sistemas.caesarservice.util.StringUtil;

@Path("/uf")
public class UfResource {

	/**
	 * Serviço utilizado para listar todas as Unidades federativas
	 * 
	 * @return
	 * @author Julio Cesar - 22/12/2018
	 */
	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listar() {

		EntityManager em = PersistenceManager.getEntityManager();
		UfDAO dao = new UfDAO(em);
		try {
			List<Uf> ufs = dao.listar();
			return Response.ok().entity(ufs).build();
		} catch (RollbackException re) {
			return Response.status(Status.BAD_REQUEST).entity("Nao pegou. Verificar.").build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para cadastrar uma nova Unidade Federativa
	 * 
	 * @param req
	 * @param descricaoEstado
	 * @return mensagem de sucesso
	 * @author Julio Cesar 22/12/2018
	 */
	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastrar(@Context HttpServletRequest req, @FormParam("uf") String uf,
			@FormParam("descricaoEstado") String descricaoEstado) {

		EntityManager em = PersistenceManager.getEntityManager();
		if (StringUtil.isEmpty(uf))
			return Response.status(Status.BAD_REQUEST).entity("Sigla da UF não informada").build();
		else if (StringUtil.isEmpty(descricaoEstado)) {
			return Response.status(Status.BAD_REQUEST).entity("Nome do Estado da UF não informado").build();
		}
		UfDAO dao = new UfDAO(em);
		Uf unidadeFederativa = new Uf();
		try {

			unidadeFederativa.setUf(uf);
			unidadeFederativa.setDescricaoEstado(descricaoEstado);
			dao.cadastrar(unidadeFederativa);

			return Response.ok().entity(unidadeFederativa).build();

		} catch (RollbackException re) {
			return Response.ok().entity("UF ja cadastrada no banco de dados.").build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para localizar uma Uniadade Federativa por Id
	 * 
	 * @param req
	 * @param iduf
	 * @return uma Unidade Federativa encontrada ou mensagem de UF não
	 *         encontrada
	 * @author Julio Cesar 22/12/2018
	 */
	@GET
	@Path("/{idUf: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultar(@Context HttpServletRequest req, @PathParam("idUf") long idUf) {

		EntityManager em = PersistenceManager.getEntityManager();
		UfDAO dao = new UfDAO(em);
		Uf uf = new Uf();
		try {
			uf = dao.localizarPorId(idUf);
			if (uf != null) {
				return Response.ok(uf).build();
			} else {
				return Response.status(Status.NOT_FOUND).entity("UF não localizada").build();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para atualizar os dados de uma Unidade Federativa
	 * 
	 * @param req
	 * @param id
	 * @param descricaouf
	 * @return
	 * @author Julio Cesar - 22/12/2018
	 */
	@POST
	@Path("{idUf: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response atualizar(@Context HttpServletRequest req, @PathParam("idUf") long idUf, @FormParam("uf") String uf,
			@FormParam("descricaoUf") String descricaoEstado) {

		if (StringUtil.isEmpty(uf))
			return Response.status(Status.BAD_REQUEST).entity("UF não informada").build();
		else if (StringUtil.isEmpty(descricaoEstado)) {
			return Response.status(Status.BAD_REQUEST).entity("Nome do Estado da UF não informado").build();
		}

		EntityManager em = PersistenceManager.getEntityManager();
		UfDAO dao = new UfDAO(em);
		Uf unidadeFederativa = new Uf();

		try {

			unidadeFederativa = dao.localizarPorId(idUf);

			unidadeFederativa.setUf(uf);
			unidadeFederativa.setDescricaoEstado(descricaoEstado);

			dao.atualizar(unidadeFederativa);

			return Response.ok(unidadeFederativa).build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para excluir uma Unidade Federativa
	 * 
	 * @param req
	 * @param iduf
	 * @return
	 * @author Julio Cesar - 22/12/2018
	 */
	@DELETE
	@Path("/{idUf: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response remover(@Context HttpServletRequest req, @PathParam("idUf") long idUf) {

		EntityManager em = PersistenceManager.getEntityManager();
		UfDAO dao = new UfDAO(em);
		Uf unidadeFederativa = new Uf();
		try {
			unidadeFederativa = dao.localizarPorId(idUf);
			if (unidadeFederativa != null) {
				dao.remover(unidadeFederativa);
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Não foi possível excluir a Unidade Federativa")
					.build();
		} finally {
			em.close();
		}
	}
}
