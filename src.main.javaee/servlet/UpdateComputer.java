package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controler.Controler;
import model.Company;
import model.Computer;

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
    try {
      String computerId = request.getParameter("computerId");
      Computer computer = Controler.getInstance().showComputerDetails(computerId).get(0);
      ArrayList<String> champs = new ArrayList<String>();
      champs.add("id");
      champs.add("name");
      ArrayList<Company> companies = Controler.getInstance().listCompaniesAll(champs);
      request.setAttribute("computerId", computerId);
      request.setAttribute("name", computer.getName());
      request.setAttribute("introduced", computer.getIntroduced());
      request.setAttribute("discontinued", computer.getDiscontinued());
      request.setAttribute("companyComputer", computer.getCompany());
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
    String computerId = request.getParameter("computerId");
    String name = request.getParameter("computerName");
    StringBuilder introduced = new StringBuilder("");
    if (!"".equals(request.getParameter("introduced"))) {
      introduced.append(request.getParameter("introduced")).append(" 00:00:00");
    }
    StringBuilder discontinued = new StringBuilder("");
    if (!"".equals(request.getParameter("discontinued"))) {
      discontinued.append(request.getParameter("discontinued")).append(" 00:00:00");
    }
    String companyId = request.getParameter("companyId");

    ArrayList<String> champs = new ArrayList<String>();
    if (name != "") {
      champs.add("name");
    }
    if (introduced.toString() != "") {
      champs.add("introduced");
    }
    if (discontinued.toString() != "") {
      champs.add("discontinued");
    }
    champs.add("company_id");
    try {
      String errorName = "";
      String errorDate = "";
      String success = "";
      if ("".equals(name)) {
        errorName = "Le nom ne doit pas être vide";
      }
      if (!"".equals(introduced.toString()) && !"".equals(discontinued.toString()) && Timestamp.valueOf(introduced.toString()).after(Timestamp.valueOf(discontinued.toString()))) {
        errorDate = "La date d'introduction doit être antérieur à la date d'interruption";
      }
      if (errorName == "" && errorDate == "") {
        Controler.getInstance().updateComputer(computerId, name, introduced.toString(), discontinued.toString(), companyId, champs);
        success = "Succès de la mise à jour";
      }
      Computer computer = Controler.getInstance().showComputerDetails(computerId).get(0);
      champs = new ArrayList<String>();
      champs.add("id");
      champs.add("name");
      ArrayList<Company> companies = Controler.getInstance().listCompaniesAll(champs);
      request.setAttribute("computerId", computerId);
      request.setAttribute("name", computer.getName());
      request.setAttribute("introduced", computer.getIntroduced());
      request.setAttribute("discontinued", computer.getDiscontinued());
      request.setAttribute("companyComputer", computer.getCompany());
      request.setAttribute("companies", companies);
      request.setAttribute("errorName", errorName);
      request.setAttribute("errorDate", errorDate);
      request.setAttribute("success", success);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
