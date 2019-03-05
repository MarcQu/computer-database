package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import controler.Controler;
import dao.ComputerFactory;
import validator.Validator;

/**
 * Servlet implementation class CreateCompany.
 */
@WebServlet("/CreateCompany")
public class CreateCompany extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/createCompany.jsp";

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   * @param request la requète
   * @param response la réponse
   * @throws ServletException ServletException
   * @throws IOException IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
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
      String name = request.getParameter("companyName");

      String errorName = "";
      String success = "";
      if (Validator.getInstance().validateName(name)) {
        errorName = "Le nom ne doit pas être vide";
      }
      if (errorName == "") {
        Controler.getInstance().createCompany(name);
        success = "Succès de la création";
      }
      request.setAttribute("errorName", errorName);
      request.setAttribute("success", success);
    } catch (IllegalArgumentException e) {
      LoggerFactory.getLogger(ComputerFactory.class).error(e.toString());
    } catch (SQLException e) {
      LoggerFactory.getLogger(ComputerFactory.class).error(e.toString());
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
