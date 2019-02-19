package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controler.Controler;
import model.Company;

/**
 * Servlet implementation class CreateComputer
 */
@WebServlet("/CreateComputer")
public class CreateComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/views/createComputer.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ArrayList<String> champs = new ArrayList<String>();
	        champs.add("id");
	        champs.add("name");
			ArrayList<Company> companies = Controler.getInstance().listCompaniesAll(champs);
			session.setAttribute("companies", companies);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced") + " 00:00:00";
		String discontinued = request.getParameter("discontinued") + " 00:00:00";
		String companyId = request.getParameter("companyId");
		try {
			Controler.getInstance().createComputer(name, introduced, discontinued, companyId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}
