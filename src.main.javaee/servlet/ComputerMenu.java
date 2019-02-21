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
import model.Computer;

/**
 * Servlet implementation class Computer.
 */
@WebServlet("/ComputerMenu")
public class ComputerMenu extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public static final String VUE = "/WEB-INF/views/computerMenu.jsp";

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
//      HttpSession session = request.getSession();
      ArrayList<String> champs = new ArrayList<String>();
      champs.add("id");
      champs.add("name");
      champs.add("introduced");
      champs.add("discontinued");
      champs.add("company_id");
      ArrayList<Computer> computers = Controler.getInstance().listComputersAll(champs);
      request.setAttribute("nombreComputers", computers.size());
      request.setAttribute("computers", computers);
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
  }
}
