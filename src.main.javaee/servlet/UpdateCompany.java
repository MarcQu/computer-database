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
    try {
      String companyId = request.getParameter("companyId");
      String search = request.getParameter("search");
      String sort = request.getParameter("sort");
      Company company = Controler.getInstance().showCompanyDetails(companyId).get(0);
      request.setAttribute("companyId", companyId);
      request.setAttribute("companyName", company.getName());
      request.setAttribute("search", search);
      request.setAttribute("sort", sort);
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
    String companyId = request.getParameter("companyId");
    String companyName = request.getParameter("companyName");

    ArrayList<String> champs = new ArrayList<String>();
    if (companyName != "") {
      champs.add("name");
    }
    try {
      String errorName = "";
      String success = "";
      if (Validator.getInstance().validateName(companyName)) {
        errorName = "Le nom ne doit pas être vide";
      }
      if (errorName == "") {
        Controler.getInstance().updateCompany(companyId, companyName, champs);
        success = "Succès de la mise à jour";
      }
      Company company = Controler.getInstance().showCompanyDetails(companyId).get(0);
      request.setAttribute("companyId", company.getId());
      request.setAttribute("companyName", company.getName());
      request.setAttribute("errorName", errorName);
      request.setAttribute("success", success);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
