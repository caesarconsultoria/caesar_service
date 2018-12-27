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
import br.com.caesar.sistemas.caesarservice.dao.UfDAO;
import br.com.caesar.sistemas.caesarservice.dao.UsuarioDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.model.TipoUsuario;
import br.com.caesar.sistemas.caesarservice.model.Uf;
import br.com.caesar.sistemas.caesarservice.model.Usuario;
import br.com.caesar.sistemas.caesarservice.util.StringUtil;

@Path("/usuario")
public class UsuarioResource {

	/**
	 * Serviço utilizado para listar todos os Usuários existentes na base
	 * 
	 * @return
	 * @author Julio Cesar - 25/12/2018
	 */
	@GET
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response listar() {

		EntityManager em = PersistenceManager.getEntityManager();
		UsuarioDAO dao = new UsuarioDAO(em);
		try {
			List<Usuario> usuarios = dao.listar();
			return Response.ok().entity(usuarios).build();
		} catch (RollbackException re) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Não foi possível listar os usuários cadastrados. Favor Verificar.")
					.build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para cadastrar um novo Usuário
	 * 
	 * @Param idTipoUsuario
	 * @Param idUf
	 * @param nome
	 * @param email
	 * @param celular
	 * @return mensagem de sucesso
	 * @author Julio Cesar 25/12/2018
	 */
	@PUT
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response cadastrar(@FormParam("idTipoUsuario") long idTipoUsuario,
			@FormParam("uf") long idUf, @FormParam("nome") String nome,
			@FormParam("email") String email,
			@FormParam("celular") String celular) {

		String erro = "";

		EntityManager em = PersistenceManager.getEntityManager();
		UsuarioDAO dao = new UsuarioDAO(em);
		Usuario usuario = new Usuario();
		TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO(em);
		UfDAO ufDAO = new UfDAO(em);

		try {

			// Localizando o Tipo de Usuário informado
			TipoUsuario tipoUsuario = new TipoUsuario();
			tipoUsuario = tipoUsuarioDAO.localizarPorId(idTipoUsuario);
			if (tipoUsuario.getIdTipoUsuario() < 0) {
				erro = "Tipo de Usuário não encontrado";
				new Exception();
			}

			// Lo1calizando a Unidade Federativa informada
			Uf uf = new Uf();
			uf = ufDAO.localizarPorId(idUf);
			if (uf.getIdUf() < 0) {
				erro = "Uf não encontrada";
				new Exception();
			}

			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setCelular(celular);
			usuario.setTipoUsuario(tipoUsuario);
			usuario.setUf(uf);

		} catch (IllegalArgumentException | NullPointerException e) {
			return Response.status(Status.BAD_REQUEST).entity(erro).build();
		}

		// validação dos campos preenchidos
		Response response = this.validarCampos(usuario);
		if (response.getStatus() >= 400) {
			return response;
		}

		try {

			dao.cadastrar(usuario);
			return Response.ok().entity(usuario).build();

		} catch (RollbackException re) {
			return Response.ok()
					.entity("Ocorreu um erro e o Usuário não foi cadastrado.")
					.build();
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para localizar um Usuário por Id
	 * 
	 * @param req
	 * @param idUsuario
	 * @return um Usuário encontrada ou mensagem de usuário não localizado
	 * @author Julio Cesar 25/12/2018
	 */
	@GET
	@Path("/{idUsuario: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response consultar(@Context HttpServletRequest req,
			@PathParam("idUsuario") long idUsuario) {

		EntityManager em = PersistenceManager.getEntityManager();
		UsuarioDAO dao = new UsuarioDAO(em);
		Usuario usuario = new Usuario();
		try {
			usuario = dao.localizarPorId(idUsuario);
			if (usuario != null) {
				return Response.ok(usuario).build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity("Usuário não localizado").build();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para atualizar os dados de um determinado usuário
	 * 
	 * @param idUsuario
	 * @param idTipoUsuario
	 * @param idUf
	 * @param nome
	 * @param email
	 * @param celular
	 * @return
	 * @author Julio Cesar - 25/12/2018
	 */
	@POST
	@Path("{idUsuario: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response atualizar(@PathParam("idUsuario") long idUsuario,
			@FormParam("tipoUsuario") long idTipoUsuario,
			@FormParam("uf") long idUf, @FormParam("nome") String nome,
			@FormParam("email") String email,
			@FormParam("celular") String celular) {

		EntityManager em = PersistenceManager.getEntityManager();
		UfDAO ufDAO = new UfDAO(em);
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);
		TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO(em);

		Uf uf = new Uf();
		Usuario usuario = new Usuario();
		TipoUsuario tipoUsuario = new TipoUsuario();

		try {

			uf = ufDAO.localizarPorId(idUf);
			usuario = usuarioDAO.localizarPorId(idUsuario);
			tipoUsuario = tipoUsuarioDAO.localizarPorId(idTipoUsuario);

			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setCelular(celular);
			usuario.setTipoUsuario(tipoUsuario);
			usuario.setUf(uf);

			return Response.ok(usuario).build();

		} finally {
			em.close();
		}
	}

	/**
	 * Serviço utilizado para excluir um Usuário
	 * 
	 * @param req
	 * @param idUsuario
	 * @return
	 * @author Julio Cesar - 25/12/2018
	 */
	@DELETE
	@Path("/{idUsuario: [0-9][0-9]*}")
	@Produces(Application.MEDIA_TYPE_JSON)
	public Response remover(@Context HttpServletRequest req,
			@PathParam("idUsuario") long idUsuario) {

		EntityManager em = PersistenceManager.getEntityManager();
		UsuarioDAO dao = new UsuarioDAO(em);
		Usuario usuario = new Usuario();
		try {
			usuario = dao.localizarPorId(idUsuario);
			if (usuario != null) {
				dao.remover(usuario);
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (RollbackException re) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Não foi possível excluir o usuário").build();
		} finally {
			em.close();
		}
	}

	/**
	 * Método utilizado para validar os campos informados no cadastro do usuário
	 * 
	 * @param usuario
	 * @return
	 * @author Julio Cesar - 25/12/2018
	 */
	private Response validarCampos(Usuario usuario) {

		if (StringUtil.isEmpty(usuario.getNome())) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Nome da usuário não informado").build();
		} else if (StringUtil.isEmpty(usuario.getEmail())) {
			return Response.status(Status.BAD_REQUEST)
					.entity("E-mail do usuário não informado").build();
		} else if (StringUtil.isEmpty(usuario.getCelular())) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Número do celular do usuário não informado")
					.build();
		}
		return Response.ok().build();
	}

}
