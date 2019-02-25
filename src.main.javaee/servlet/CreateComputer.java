package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controler.Controler;
import model.Company;

/**
 * Servlet implementation class CreateComputer.
 */
@WebServlet("/CreateComputer")
public class CreateComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/createComputer.jsp";

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
      ArrayList<String> champs = new ArrayList<String>();
      champs.add("id");
      champs.add("name");
      ArrayList<Company> companies = Controler.getInstance().listCompaniesAll(champs);
      request.setAttribute("companies", companies);
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
    try {
      String name = request.getParameter("computerName");
      StringBuilder introduced = new StringBuilder(request.getParameter("introduced"));
      if (!"".equals(introduced.toString())) {
        introduced.append(" 00:00:00");
      }
      StringBuilder discontinued = new StringBuilder(request.getParameter("discontinued"));
      if (!"".equals(discontinued.toString())) {
        discontinued.append(" 00:00:00");
      }
      String companyId = request.getParameter("companyId");

      ArrayList<String> champs = new ArrayList<String>();
      champs.add("id");
      champs.add("name");
      ArrayList<Company> companies = Controler.getInstance().listCompaniesAll(champs);
      request.setAttribute("companies", companies);
      Controler.getInstance().createComputer(name, introduced.toString(), discontinued.toString(), companyId);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
