package br.com.caesar.sistemas.caesarservice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caesar.sistemas.caesarservice.util.Constantes;
import br.com.caesar.sistemas.caesarservice.util.ServletUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { Constantes.PAGINA_DASHBOARD + ".html" })

public class DashboardController extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletUtil.noCache(res);
		// Usuario usuarioLogado = (Usuario)
		// req.getSession().getAttribute("usuario");
		// if (usuarioLogado != null) {
		// req.getRequestDispatcher(Constantes.PAGINA_DASHBOARD +
		// ".jsp").forward(req, res);
		// } else {
		// res.sendRedirect(req.getContextPath() + Constantes.PAGINA_LOGIN);
		// }
	}
}
