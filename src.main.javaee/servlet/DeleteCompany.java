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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.CompanyTO;
import mapper.CompanyMapper;
import model.Company;
import service.CompanyService;

/**
 * Servlet implementation class DeleteCompany.
 */
@WebServlet("/DeleteCompany")
public class DeleteCompany extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/companyMenu.jsp";

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   * @param request la requ�te
   * @param response la r�ponse
   * @throws ServletException ServletException
   * @throws IOException      IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Logger logger = LoggerFactory.getLogger(DeleteCompany.class);
    HttpSession session = request.getSession();
    String search = request.getParameter("search");
    String sort = request.getParameter("sort");

    int nombre = Integer.parseInt(request.getParameter("nombre"));
    int page = Integer.parseInt(request.getParameter("page"));

    String selected = request.getParameter("selected");
    try {
      if (selected != null) {
        String[] selectedCompanies = selected.split(",");
        for (String company : selectedCompanies) {
          CompanyService.getInstance().deleteCompany(company);
        }
      }

      int nombreCompanies = CompanyService.getInstance().countCompanies(search);
      ArrayList<Company> companies = CompanyService.getInstance().listCompanies(nombre, nombre * (page - 1), search, sort);
      ArrayList<CompanyTO> companiesTO = CompanyMapper.getInstance().getCompanyTO(companies);
      request.setAttribute("nombreCompanies", nombreCompanies);
      request.setAttribute("companies", companiesTO);

      int pages = nombreCompanies / nombre + 1;
      session.setAttribute("nombre", nombre);
      session.setAttribute("page", page);
      request.setAttribute("pages", pages);
      request.setAttribute("search", search);
      request.setAttribute("sort", sort);
    } catch (SQLException e) {
      logger.error(e.toString());
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   * @param request la requ�te
   * @param response la r�ponse
   * @throws ServletException ServletException
   * @throws IOException      IOException
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }
}
