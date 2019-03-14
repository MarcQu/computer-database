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

import dto.ComputerTO;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class DeleteComputer.
 */
//@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/computerMenu.jsp";
  private Logger logger;

  @Autowired
  private ComputerService computerService;

  @Autowired
  private CompanyService companyService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.logger = LoggerFactory.getLogger(DeleteComputer.class);
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
    HttpSession session = request.getSession();
    String search = request.getParameter("search");
    String sort = request.getParameter("sort");
    int nombre = Integer.parseInt(request.getParameter("nombre"));
    int page = Integer.parseInt(request.getParameter("page"));
    try {
      String selected = request.getParameter("selected");
      if (selected != null) {
        String[] selectedComputers = selected.split(",");
        for (String id : selectedComputers) {
          ComputerTO computerTO = new ComputerTO();
          computerTO.setId(id);
          computerService.deleteComputer(computerTO);
        }
      }

      int nombreComputers = computerService.countComputers(search);
      List<ComputerTO> computers = computerService.listComputers(nombre, nombre * (page - 1), search, sort);
      request.setAttribute("nombreComputers", nombreComputers);
      request.setAttribute("computers", computers);

      int pages = nombreComputers / nombre + 1;
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
