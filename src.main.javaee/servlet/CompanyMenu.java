package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import dto.CompanyTO;
import service.CompanyService;

/**
 * Servlet implementation class Company.
 */
//@WebServlet("/CompanyMenu")
public class CompanyMenu extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final String VUE = "/WEB-INF/views/companyMenu.jsp";
  private Logger logger;

  @Autowired
  private CompanyService companyService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.logger = LoggerFactory.getLogger(CompanyMenu.class);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

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
      String sort = request.getParameter("sort");

      int nombre = Integer.parseInt(request.getParameter("nombre"));
      int page = Integer.parseInt(request.getParameter("page"));
      int nombreCompanies = companyService.countCompanies(search);

      List<CompanyTO> companies = companyService.listCompanies(nombre, nombre * (page - 1), search, sort);
      request.setAttribute("nombreCompanies", nombreCompanies);
      request.setAttribute("companies", companies);

      int pages = nombreCompanies / nombre + 1;
      session.setAttribute("nombre", nombre);
      session.setAttribute("page", page);
      request.setAttribute("pages", pages);
      request.setAttribute("search", search);
      request.setAttribute("sort", sort);
    } catch (SQLException e) {
      this.logger.error(e.toString());
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
