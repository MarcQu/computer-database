package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controler.Controler;
import model.Company;

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
      HttpSession session = request.getSession();
      String computerId = request.getParameter("computerId");
      ArrayList<String> champs = new ArrayList<String>();
      champs.add("id");
      champs.add("name");
      ArrayList<Company> companies = Controler.getInstance().listCompaniesAll(champs);
      session.setAttribute("companies", companies);
      session.setAttribute("computerId", computerId);
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
    HttpSession session = request.getSession();
    String computerId = (String)session.getAttribute("computerId");
    System.out.println(computerId);
    String name = request.getParameter("computerName");
    String introduced = "";
    if (!"".equals(request.getParameter("introduced"))) {
      introduced = request.getParameter("introduced") + " 00:00:00";
    }
    String discontinued = "";
    if (!"".equals(request.getParameter("discontinued"))) {
      discontinued = request.getParameter("discontinued") + " 00:00:00";
    }
    String companyId = request.getParameter("companyId");
    ArrayList<String> champs = new ArrayList<String>();
    if (name != "") {
      champs.add("name");
    }
    if (introduced != "") {
      champs.add("introduced");
    }
    if (discontinued != "") {
      champs.add("discontinued");
    }
    if (companyId != "") {
      champs.add("company_id");
    }
    try {
      Controler.getInstance().updateComputer(computerId, name, introduced, discontinued, companyId, champs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    request.setAttribute("introduced", introduced);
    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
  }
}
