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

import exception.EmptyNameException;
import exception.SpecialCharacterException;
import model.Company;
import service.CompanyService;
import validator.Validator;

/**
 * Servlet implementation class UpdateComputer.
 */
@WebServlet("/UpdateCompany")
public class UpdateCompany extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/updateCompany.jsp";

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   * @param request la requète
   * @param response la réponse
   * @throws ServletException ServletException
   * @throws IOException      IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Logger logger = LoggerFactory.getLogger(UpdateCompany.class);
    String companyId = request.getParameter("companyId");
    String search = request.getParameter("search");
    String sort = request.getParameter("sort");
    try {
      Company company = CompanyService.getInstance().showCompanyDetails(companyId).get(0);
      request.setAttribute("companyId", companyId);
      request.setAttribute("companyName", company.getName());
      request.setAttribute("search", search);
      request.setAttribute("sort", sort);
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
    Logger logger = LoggerFactory.getLogger(UpdateCompany.class);
    String companyId = request.getParameter("companyId");
    String companyName = request.getParameter("companyName");
    try {
      Validator.getInstance().validateField(companyName);
      Validator.getInstance().validateName(companyName);
      ArrayList<String> champs = new ArrayList<String>();
      champs.add("name");
      CompanyService.getInstance().updateCompany(companyId, companyName, champs);
      Company company = CompanyService.getInstance().showCompanyDetails(companyId).get(0);
      request.setAttribute("companyId", company.getId());
      request.setAttribute("companyName", company.getName());
      request.setAttribute("success", "SuccÃ¨s de la mise Ã  jour");
    } catch (SQLException e) {
      logger.error(e.toString());
    } catch (EmptyNameException e) {
      try {
        Company company = CompanyService.getInstance().showCompanyDetails(companyId).get(0);
        request.setAttribute("companyId", company.getId());
        request.setAttribute("companyName", company.getName());
        request.setAttribute("errorName", "Le nom ne doit pas Ãªtre vide");
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    } catch (SpecialCharacterException e) {
      try {
        Company company = CompanyService.getInstance().showCompanyDetails(companyId).get(0);
        request.setAttribute("companyId", company.getId());
        request.setAttribute("companyName", company.getName());
        request.setAttribute("errorName", "Le champ ne doit pas contenir de caractÃ¨res spÃ©ciaux (\"#;)");
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
