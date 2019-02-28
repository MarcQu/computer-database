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
import dto.ComputerTO;
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
      HttpSession session = request.getSession();
      String search = request.getParameter("search");
      int nombre = Integer.parseInt(request.getParameter("nombre"));
      System.out.println(nombre);
      int page = Integer.parseInt(request.getParameter("page"));
      System.out.println(page);
      int nombreComputers = Controler.getInstance().countComputers(new StringBuilder("%").append(search).append("%").toString());

      ArrayList<Computer> computers = Controler.getInstance().listComputers(nombre, nombre * (page - 1), new StringBuilder("%").append(search).append("%").toString());
      ArrayList<ComputerTO> computersTO = Controler.getInstance().getComputerData(computers);
      request.setAttribute("nombreComputers", nombreComputers);
      request.setAttribute("computers", computersTO);

      int pages = nombreComputers / nombre + 1;
      session.setAttribute("nombre", nombre);
      session.setAttribute("page", page);
      request.setAttribute("pages", pages);
      request.setAttribute("search", search);
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
