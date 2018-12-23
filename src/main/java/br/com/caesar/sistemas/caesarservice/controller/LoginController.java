package br.com.caesar.sistemas.caesarservice.controller;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caesar.sistemas.caesarservice.dao.ConfiguracaoDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;
import br.com.caesar.sistemas.caesarservice.util.ServletUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/login.html", "/sair.html" })
public class LoginController extends HttpServlet {

	private static final String URL_LOGIN = "/login.html";
	private static final String PAGINA_LOGIN = "/login.jsp";
	private static final String CAPTCHA_HABILITADO = "captcha.habilitado";
	private static final String CAPTCHA_SITEKEY = "captcha.sitekey";
	// private static final String URL_FIM_SESSAO = "webticket.url_fim_sessao";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.noCache(resp);

		/* Finaliza a sessão caso /login.html seja acessado */
		if (req.getRequestURI().endsWith("/login.html")) {
			req.getSession().invalidate();
		}

		if (req.getRequestURI().endsWith("/sair.html")) {
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath() + URL_LOGIN);
			return;
		}
		EntityManager em = PersistenceManager.getEntityManager();
		ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(em);
		req.setAttribute("captcha", configuracaoDAO.getAsString(CAPTCHA_HABILITADO));
		req.setAttribute("sitekey", configuracaoDAO.getAsString(CAPTCHA_SITEKEY));
		req.setAttribute("cssTela", ServletUtil.getCookie("cssTela", req)); // Seleciona
																			// CSS
																			// selecionado
																			// pelo
																			// usuário
		req.setCharacterEncoding("UTF-8");
		req.getRequestDispatcher(PAGINA_LOGIN).forward(req, resp);
		em.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.noCache(resp);

		/*
		 * req.setCharacterEncoding("UTF-8");
		 * 
		 * List<String> mensagensErro = new ArrayList<>();
		 * 
		 * String login = req.getParameter("login"); String senha =
		 * req.getParameter("senha"); String siglaOrgao =
		 * req.getParameter("orgao"); String captcha =
		 * req.getParameter("g-recaptcha-response");
		 * 
		 * // Verifica se foi informado um órgão válido Orgao orgao = null; try
		 * { orgao = Orgao.valueOf(siglaOrgao); } catch
		 * (IllegalArgumentException | NullPointerException e) {
		 * mensagensErro.add("Login ou senha inválidos"); } EntityManager em =
		 * PersistenceManager.getEntityManager(); ConfiguracaoDAO
		 * configuracaoDAO = new ConfiguracaoDAO(em);
		 * 
		 * try { // Se foi informado um órgao válido, continua if (orgao !=
		 * null) { UsuarioDAO usuarioDao = new UsuarioDAO(em); Usuario usuario =
		 * usuarioDao.localizaPorLogin(login, orgao); ConfiguracaoDAO configDao
		 * = new ConfiguracaoDAO(em);
		 * 
		 * if (usuario == null) { // usuário não localizado
		 * mensagensErro.add("Usuário não cadastrado"); } else if
		 * (configDao.getAsBoolean("captcha.habilitado") && !new
		 * ReCaptcha().validate(captcha)) { // CAPTCHA // não // preenchido
		 * mensagensErro.add("Não foi possível validar o CAPTCHA"); } else if
		 * (usuario.isBloqueado()) { // usuário bloqueado mensagensErro.
		 * add("Usuário bloqueado devido ao número de tentativas de acesso"); }
		 * else if (!usuario.isAtivo()) { // usuário inativo
		 * mensagensErro.add("Usuário inativo"); } else { // CAPTCHA foi
		 * preenchido, usuário não está bloqueado e // está ativo. Tenta
		 * autenticar. boolean autenticado = false; switch
		 * (usuario.getTipoAutenticacao()) { case LDAP: { autenticado =
		 * AutenticacaoLDAP.autentica(usuario, senha); break; } case SISTEMA: {
		 * autenticado = AutenticacaoSistema.autentica(usuario, senha); break; }
		 * }
		 * 
		 * if (autenticado) { // usuário conseguiu autenticar
		 * req.getSession().setAttribute("usuario", usuario);
		 * req.getSession().setAttribute("urlFimSessao",
		 * configuracaoDAO.getAsString(URL_FIM_SESSAO));
		 * req.getSession().setAttribute("tentativas", 0);
		 * resp.sendRedirect("dashboard.html"); return; }
		 * 
		 * long maximoTentativas = configDao.getAsLong("login.tentativas", 10);
		 * 
		 * if (maximoTentativas > 0) { // usuário não conseguiu // autenticar.
		 * Conta número de // tentativas, se for o caso. int tentativas; try {
		 * tentativas = req.getSession().getAttribute("tentativas") != null ?
		 * Integer.parseInt(req.getSession().getAttribute("tentativas").toString
		 * ()) : 0; } catch (Exception e) { tentativas = 0; }
		 * req.getSession().setAttribute("tentativas", ++tentativas); if
		 * (tentativas >= maximoTentativas) { usuario.setBloqueado(true);
		 * usuarioDao.atualiza(usuario); mensagensErro.
		 * add("Usuário bloqueado devido ao número de tentativas de acesso"); }
		 * else { mensagensErro.add("Login ou senha inválidos"); } } else {
		 * mensagensErro.add("Login ou senha inválidos"); } } }
		 * 
		 * // não consegui localizar o usuário e autenticar. Voltar para a //
		 * página de login. req.setAttribute("captcha",
		 * configuracaoDAO.getAsString(CAPTCHA_HABILITADO));
		 * req.setAttribute("sitekey",
		 * configuracaoDAO.getAsString(CAPTCHA_SITEKEY));
		 * req.setAttribute("cssTela", ServletUtil.getCookie("cssTela", req));
		 * // Seleciona // CSS // selecionado // pelo // usuário
		 * req.setAttribute("erros", mensagensErro);
		 * req.getRequestDispatcher(PAGINA_LOGIN).forward(req, resp);
		 * 
		 * } finally { em.close(); } }
		 */
	}
}
