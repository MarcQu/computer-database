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

import dto.CompanyTO;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import model.Company;
import service.CompanyService;

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
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(companyId);
    try {
      Company company = CompanyService.getInstance().showCompanyDetails(companyTO).get(0);
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
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(companyId);
    companyTO.setName(companyName);
    try {
      ArrayList<String> champs = new ArrayList<String>();
      champs.add("name");
      CompanyService.getInstance().updateCompany(companyTO, champs);
      displayInformation(request, companyTO);
      request.setAttribute("success", "Succès de la mise à jour");
    } catch (SQLException e) {
      logger.error(e.toString());
    } catch (EmptyNameException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, companyTO);
        request.setAttribute("errorName", error);
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    } catch (SpecialCharacterException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, companyTO);
        request.setAttribute("error", error);
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }

  /**
   * Réaffiche les informations de la compagnie.
   * @param request la requète de la page
   * @param companyTO la compagnie à afficher
   * @throws SQLException SQLException
   */
  private void displayInformation(HttpServletRequest request, CompanyTO companyTO) throws SQLException {
    Company company = CompanyService.getInstance().showCompanyDetails(companyTO).get(0);
    request.setAttribute("companyId", company.getId());
    request.setAttribute("companyName", company.getName());
  }
}
