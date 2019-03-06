package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controler.Controler;
import exception.DateException;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import model.Company;
import validator.Validator;

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
   * @throws IOException IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Logger logger = LoggerFactory.getLogger(CreateComputer.class);
    try {
      ArrayList<Company> companies = Controler.getInstance().listCompaniesAll();
      request.setAttribute("companies", companies);
    } catch (SQLException e) {
      logger.error(e.toString());
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
    Logger logger = LoggerFactory.getLogger(CreateComputer.class);
    String name = request.getParameter("computerName");
    StringBuilder introduced = new StringBuilder(request.getParameter("introduced"));
    StringBuilder discontinued = new StringBuilder(request.getParameter("discontinued"));
    String companyId = request.getParameter("companyId");

    if (!"".equals(introduced.toString())) {
      introduced.append(" 00:00:00");
    }
    if (!"".equals(discontinued.toString())) {
      discontinued.append(" 00:00:00");
    }
    try {
      ArrayList<Company> companies = Controler.getInstance().listCompaniesAll();
      request.setAttribute("companies", companies);
      Validator.getInstance().validateField(name);
      Validator.getInstance().validateField(introduced.toString());
      Validator.getInstance().validateField(discontinued.toString());
      Validator.getInstance().validateField(companyId);
      Validator.getInstance().validateName(name);
      Validator.getInstance().validateDate(introduced.toString(), discontinued.toString());
      Controler.getInstance().createComputer(name, introduced.toString(), discontinued.toString(), companyId);
      request.setAttribute("success", "Succès de la création");
    } catch (SQLException e) {
      logger.error(e.toString());
    } catch (EmptyNameException e) {
      request.setAttribute("errorName", "Le nom ne doit pas être vide");
      logger.error(e.toString());
    } catch (DateException e) {
      request.setAttribute("errorDate", "La date d'introduction doit être antérieur à la date d'interruption");
      logger.error(e.toString());
    } catch (SpecialCharacterException e) {
      request.setAttribute("errorName", "Le champ ne doit pas contenir de caractères spéciaux (\"#;)");
      logger.error(e.toString());
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
