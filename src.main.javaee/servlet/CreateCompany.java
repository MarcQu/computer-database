package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.EmptyNameException;
import exception.SpecialCharacterException;
import service.CompanyService;
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
   * @param request la requ�te
   * @param response la r�ponse
   * @throws ServletException ServletException
   * @throws IOException IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   * @param request la requ�te
   * @param response la r�ponse
   * @throws ServletException ServletException
   * @throws IOException IOException
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Logger logger = LoggerFactory.getLogger(CreateCompany.class);
    try {
      String name = request.getParameter("companyName");
      Validator.getInstance().validateField(name);
      Validator.getInstance().validateName(name);
      CompanyService.getInstance().createCompany(name);
      request.setAttribute("success", "Succès de la création");
    } catch (SQLException e) {
      logger.error(e.toString());
    } catch (EmptyNameException e) {
      request.setAttribute("errorName", "Le nom ne doit pas être vide");
      logger.error(e.toString());
    } catch (SpecialCharacterException e) {
      request.setAttribute("errorName", "Le champ ne doit pas contenir de caractères spéciaux (\"#;)");
      logger.error(e.toString());
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
