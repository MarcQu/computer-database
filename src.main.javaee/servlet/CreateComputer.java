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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import dto.CompanyTO;
import dto.ComputerTO;
import exception.DateFormatException;
import exception.DatePrecedenceException;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import model.Company;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class CreateComputer.
 */
@WebServlet("/CreateComputer")
public class CreateComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/createComputer.jsp";
  private Logger logger;

  @Autowired
  private ComputerService computerService;

  @Autowired
  private CompanyService companyService;

  @Override
  public void init() throws ServletException {
    super.init();
    this.logger = LoggerFactory.getLogger(CreateComputer.class);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   * @param request la requète
   * @param response la réponse
   * @throws ServletException ServletException
   * @throws IOException IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      ArrayList<CompanyTO> companies = companyService.listCompaniesAll();
      request.setAttribute("companies", companies);
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
    String name = request.getParameter("computerName");
    StringBuilder introduced = new StringBuilder(request.getParameter("introduced"));
    StringBuilder discontinued = new StringBuilder(request.getParameter("discontinued"));
    String companyId = request.getParameter("companyId");

    ComputerTO computerTO = new ComputerTO();
    computerTO.setName(name);
    computerTO.setIntroduced(introduced.toString());
    computerTO.setDiscontinued(discontinued.toString());
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(companyId);
    try {
      ArrayList<Company> company = companyService.showCompanyDetails(companyTO);
      if (company.size() > 0) {
        computerTO.setCompany(company.get(0));
      }
      ArrayList<CompanyTO> companies = companyService.listCompaniesAll();
      request.setAttribute("companies", companies);
      computerService.createComputer(computerTO);
      request.setAttribute("success", "Succès de la création");
    } catch (SQLException e) {
      this.logger.error(e.toString());
    } catch (EmptyNameException e) {
      String error = e.toString().split(": ")[1];
      request.setAttribute("errorName", error);
      this.logger.error(e.toString());
    } catch (DateFormatException e) {
      String error = e.toString().split(": ")[1];
      request.setAttribute("errorDate", error);
      this.logger.error(e.toString());
    } catch (DatePrecedenceException e) {
      String error = e.toString().split(": ")[1];
      request.setAttribute("errorDate", error);
      this.logger.error(e.toString());
    } catch (SpecialCharacterException e) {
      String error = e.toString().split(": ")[1];
      request.setAttribute("error", error);
      this.logger.error(e.toString());
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
