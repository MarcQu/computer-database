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

import exception.DateFormatException;
import exception.DatePrecedenceException;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class UpdateComputer.
 */
@WebServlet("/UpdateComputer")
public class UpdateComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/updateComputer.jsp";

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   * @param request la requète
   * @param response la réponse
   * @throws ServletException ServletException
   * @throws IOException      IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Logger logger = LoggerFactory.getLogger(UpdateComputer.class);
    String computerId = request.getParameter("computerId");
    String search = request.getParameter("search");
    String sort = request.getParameter("sort");
    try {
      Computer computer = ComputerService.getInstance().showComputerDetails(computerId).get(0);
      ArrayList<Company> companies = CompanyService.getInstance().listCompaniesAll();
      request.setAttribute("computerId", computerId);
      request.setAttribute("computerName", computer.getName());
      request.setAttribute("introduced", computer.getIntroduced());
      request.setAttribute("discontinued", computer.getDiscontinued());
      request.setAttribute("companyComputer", computer.getCompany());
      request.setAttribute("companies", companies);
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
    Logger logger = LoggerFactory.getLogger(UpdateComputer.class);
    String computerId = request.getParameter("computerId");
    String computerName = request.getParameter("computerName");
    StringBuilder introduced = new StringBuilder("");
    StringBuilder discontinued = new StringBuilder("");
    String companyId = request.getParameter("companyId");

    if (!"".equals(request.getParameter("introduced"))) {
      introduced.append(request.getParameter("introduced")).append(" 00:00:00");
    }
    if (!"".equals(request.getParameter("discontinued"))) {
      discontinued.append(request.getParameter("discontinued")).append(" 00:00:00");
    }

    ArrayList<String> champs = new ArrayList<String>();
    champs.add("company_id");
    if (computerName != "") {
      champs.add("name");
    }
    if (introduced.toString() != "") {
      champs.add("introduced");
    }
    if (discontinued.toString() != "") {
      champs.add("discontinued");
    }
    try {
      ArrayList<Company> companies = CompanyService.getInstance().listCompaniesAll();
      request.setAttribute("companies", companies);
      ComputerService.getInstance().updateComputer(computerId, computerName, introduced.toString(), discontinued.toString(), companyId, champs);

      displayInformation(request, computerId);
      request.setAttribute("success", "Succès de la mise à jour");
    } catch (SQLException e) {
      logger.error(e.toString());
    } catch (EmptyNameException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerId);
        request.setAttribute("errorName", error);
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    } catch (DateFormatException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerId);
        request.setAttribute("errorDate", error);
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    } catch (DatePrecedenceException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerId);
        request.setAttribute("errorDate", error);
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    } catch (SpecialCharacterException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerId);
        request.setAttribute("error", error);
        logger.error(e.toString());
      } catch (SQLException e1) {
        logger.error(e1.toString());
      }
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }

  /**
   * Réaffiche les informations de l'ordinateur.
   * @param request la requète de la page
   * @param computerId l'id de l'ordinateur à afficher
   * @throws SQLException SQLException
   */
  private void displayInformation(HttpServletRequest request, String computerId) throws SQLException {
    Computer computer = ComputerService.getInstance().showComputerDetails(computerId).get(0);
    request.setAttribute("computerId", computer.getId());
    request.setAttribute("computerName", computer.getName());
    request.setAttribute("introduced", computer.getIntroduced());
    request.setAttribute("discontinued", computer.getDiscontinued());
    request.setAttribute("companyComputer", computer.getCompany());
  }
}
