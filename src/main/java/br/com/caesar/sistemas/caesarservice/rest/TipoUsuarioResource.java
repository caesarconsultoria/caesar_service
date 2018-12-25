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
import br.com.caesar.sistemas.caesarservice.dao.TipoUsuarioDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.model.TipoUsuario;
import br.com.caesar.sistemas.caesarservice.util.StringUtil;

@Path("/tipousuario")
public class TipoUsuarioResource {

	/**
	 * Serviço utilizado para listar todos os Tipos de usuários existentes
	 * 
	 * @return
	 * @author Julio Cesar - 25/12/2018
	 */
	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listar() {

		EntityManager em = PersistenceManager.getEntityManager();
		TipoUsuarioDAO dao = new TipoUsuarioDAO(em);
		try {
			List<TipoUsuario> tipoUsuario = dao.listar();
			return Response.ok().entity(tipoUsuario).build();
		} catch (RollbackException re) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Não foi possível listar todos os Tipos de Usuários existentes.").build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para cadastrar um novo Tipo de Usuário
	 * 
	 * @param req
	 * @param descricaoTipoUsuario
	 * @return mensagem de sucesso
	 * @author Julio Cesar 25/12/2018
	 */
	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastrar(@Context HttpServletRequest req,
			@FormParam("descricaoTipoUsuario") String descricaoTipoUsuario) {

		EntityManager em = PersistenceManager.getEntityManager();

		if (StringUtil.isEmpty(descricaoTipoUsuario)) {
			return Response.status(Status.BAD_REQUEST).entity("Descrição do Tipo de Usuário não informado").build();
		}

		TipoUsuarioDAO dao = new TipoUsuarioDAO(em);
		TipoUsuario tipoUsuario = new TipoUsuario();

		try {
			tipoUsuario.setDescricaoTipoUsuario(descricaoTipoUsuario);
			dao.cadastrar(tipoUsuario);
			return Response.ok().entity(tipoUsuario).build();
		} catch (RollbackException re) {
			return Response.ok().entity("Tipo de Usuário já cadastrado no banco de dados.").build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para localizar um Tipo de Usuário por Id
	 * 
	 * @param req
	 * @param idTipoUsuario
	 * @return um Tipo de usuário encontrado ou mensagem de Tipo de Usuário não
	 *         encontrado
	 * @author Julio Cesar 25/12/2018
	 */
	@GET
	@Path("/{idTipoUsuario: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultar(@Context HttpServletRequest req, @PathParam("idTipoUsuario") long idTipoUsuario) {

		EntityManager em = PersistenceManager.getEntityManager();
		TipoUsuarioDAO dao = new TipoUsuarioDAO(em);
		TipoUsuario tipoUsuario = new TipoUsuario();
		try {
			tipoUsuario = dao.localizarPorId(idTipoUsuario);
			if (tipoUsuario != null) {
				return Response.ok(tipoUsuario).build();
			} else {
				return Response.status(Status.NOT_FOUND).entity("Tipo de Usuário não localizado").build();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para atualizar um Tipo de Usuário existente
	 * 
	 * @param req
	 * @param id
	 * @param descricaoTipoUsuario
	 * @return
	 * @author Julio Cesar - 25/12/2018
	 */
	@POST
	@Path("{idTipoUsuario: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response atualizar(@Context HttpServletRequest req, @PathParam("idTipoUsuario") long idTipoUsuario,
			@FormParam("descricaoTipoUsuario") String descricaoTipoUsuario) {

		if (StringUtil.isEmpty(descricaoTipoUsuario))
			return Response.status(Status.BAD_REQUEST).entity("Tipo de Usuário não informado").build();

		EntityManager em = PersistenceManager.getEntityManager();
		TipoUsuarioDAO dao = new TipoUsuarioDAO(em);
		TipoUsuario tipoUsuario = new TipoUsuario();
		try {
			tipoUsuario = dao.localizarPorId(idTipoUsuario);
			tipoUsuario.setDescricaoTipoUsuario(descricaoTipoUsuario);
			dao.atualizar(tipoUsuario);
			return Response.ok(tipoUsuario).build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para excluir um Tipo de Usuário
	 * 
	 * @param req
	 * @param idTipoUsuario
	 * @return
	 * @author Julio Cesar - 25/12/2018
	 */
	@DELETE
	@Path("/{idTipoUsuario: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response remover(@Context HttpServletRequest req, @PathParam("idTipoUsuario") long idTipoUsuario) {

		EntityManager em = PersistenceManager.getEntityManager();
		TipoUsuarioDAO dao = new TipoUsuarioDAO(em);
		TipoUsuario tipoUsuario = new TipoUsuario();
		try {
			tipoUsuario = dao.localizarPorId(idTipoUsuario);
			if (tipoUsuario != null) {
				dao.remover(tipoUsuario);
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Não foi possível excluir o Tipo de Usuário")
					.build();
		} finally {
			em.close();
		}
	}
}
