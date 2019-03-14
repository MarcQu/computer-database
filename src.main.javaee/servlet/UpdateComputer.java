package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import dto.CompanyTO;
import dto.ComputerTO;
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
//@WebServlet("/UpdateComputer")
public class UpdateComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/updateComputer.jsp";
  private Logger logger;

  @Autowired
  private ComputerService computerService;

  @Autowired
  private CompanyService companyService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.logger = LoggerFactory.getLogger(UpdateComputer.class);
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
    String computerId = request.getParameter("computerId");
    String search = request.getParameter("search");
    String sort = request.getParameter("sort");
    ComputerTO computerTO = new ComputerTO();
    computerTO.setId(computerId);
    try {
      Computer computer = computerService.showComputerDetails(computerTO).get(0);
      List<CompanyTO> companies = companyService.listCompaniesAll();
      request.setAttribute("computerId", computerId);
      request.setAttribute("computerName", computer.getName());
      request.setAttribute("introduced", computer.getIntroduced());
      request.setAttribute("discontinued", computer.getDiscontinued());
      request.setAttribute("companyComputer", computer.getCompany());
      request.setAttribute("companies", companies);
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
    String computerId = request.getParameter("computerId");
    String computerName = request.getParameter("computerName");
    StringBuilder introduced = new StringBuilder(request.getParameter("introduced"));
    StringBuilder discontinued = new StringBuilder(request.getParameter("discontinued"));
    String companyId = request.getParameter("companyId");

    ArrayList<String> champs = new ArrayList<String>();
    if (computerName != "") {
      champs.add("name");
    }
    if (introduced.toString() != "") {
      champs.add("introduced");
    }
    if (discontinued.toString() != "") {
      champs.add("discontinued");
    }
    champs.add("company_id");

    ComputerTO computerTO = new ComputerTO();
    computerTO.setId(computerId);
    computerTO.setName(computerName);
    computerTO.setIntroduced(introduced.toString());
    computerTO.setDiscontinued(discontinued.toString());
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(companyId);
    try {
      List<Company> company = companyService.showCompanyDetails(companyTO);
      if (company.size() > 0) {
        computerTO.setCompany(company.get(0));
      }
      List<CompanyTO> companies = companyService.listCompaniesAll();
      request.setAttribute("companies", companies);
      computerService.updateComputer(computerTO, champs);

      displayInformation(request, computerTO);
      request.setAttribute("success", "Succès de la mise à jour");
    } catch (SQLException e) {
      this.logger.error(e.toString());
    } catch (EmptyNameException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerTO);
        request.setAttribute("errorName", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    } catch (DateFormatException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerTO);
        request.setAttribute("errorDate", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    } catch (DatePrecedenceException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerTO);
        request.setAttribute("errorDate", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    } catch (SpecialCharacterException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(request, computerTO);
        request.setAttribute("error", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }

  /**
   * Réaffiche les informations de l'ordinateur.
   * @param request la requète de la page
   * @param computerTO l'ordinateur à afficher
   * @throws SQLException SQLException
   */
  private void displayInformation(HttpServletRequest request, ComputerTO computerTO) throws SQLException {
    Computer computer = computerService.showComputerDetails(computerTO).get(0);
    request.setAttribute("computerId", computer.getId());
    request.setAttribute("computerName", computer.getName());
    request.setAttribute("introduced", computer.getIntroduced());
    request.setAttribute("discontinued", computer.getDiscontinued());
    request.setAttribute("companyComputer", computer.getCompany());
  }
}
