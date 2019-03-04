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
import dto.CompanyTO;
import model.Company;

/**
 * Servlet implementation class DeleteCompany.
 */
@WebServlet("/DeleteCompany")
public class DeleteCompany extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/companyMenu.jsp";

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   * @param request la requète
   * @param response la réponse
   * @throws ServletException ServletException
   * @throws IOException      IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      String search = request.getParameter("search");
      int nombre = Integer.parseInt(request.getParameter("nombre"));
      int page = Integer.parseInt(request.getParameter("page"));

      String selected = request.getParameter("selected");
      String[] selectedCompanies = selected.split(",");
      for (String company : selectedCompanies) {
         Controler.getInstance().deleteCompany(company);
      }

      int nombreCompanies = Controler.getInstance().countCompanies(search);
      ArrayList<Company> companies = Controler.getInstance().listCompanies(nombre, nombre * (page - 1), search);
      ArrayList<CompanyTO> companiesTO = Controler.getInstance().getCompanyData(companies);
      request.setAttribute("nombreCompanies", nombreCompanies);
      request.setAttribute("companies", companiesTO);

      int pages = nombreCompanies / nombre + 1;
      session.setAttribute("nombre", nombre);
      session.setAttribute("page", page);
      request.setAttribute("pages", pages);
      request.setAttribute("search", search);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   * @param request la requète
   * @param response la réponse
   * @throws ServletException ServletException
   * @throws IOException      IOException
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }
}
