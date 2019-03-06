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

import org.slf4j.LoggerFactory;

import controler.Controler;
import dao.ComputerFactory;
import dto.ComputerTO;
import mapper.ComputerMapper;
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
   * @throws IOException IOException
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      String search = request.getParameter("search");
      String sort = request.getParameter("sort");
      int nombre = Integer.parseInt(request.getParameter("nombre"));
      int page = Integer.parseInt(request.getParameter("page"));
      int nombreComputers = Controler.getInstance().countComputers(search);

      ArrayList<Computer> computers = Controler.getInstance().listComputers(nombre, nombre * (page - 1), search, sort);
      ArrayList<ComputerTO> computersTO = ComputerMapper.getInstance().getComputerTO(computers);
      request.setAttribute("nombreComputers", nombreComputers);
      request.setAttribute("computers", computersTO);

      int pages = nombreComputers / nombre + 1;
      session.setAttribute("nombre", nombre);
      session.setAttribute("page", page);
      request.setAttribute("pages", pages);
      request.setAttribute("search", search);
      request.setAttribute("sort", sort);
    } catch (SQLException e) {
      LoggerFactory.getLogger(ComputerFactory.class).error(e.toString());
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
