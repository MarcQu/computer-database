package mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.ComputerTO;
import model.Computer;

public class ComputerMapper {
  private static ComputerMapper instance = null;

  /**
   * ComputerMapper contient les méthodes gérant les DTOs.
   * @throws SQLException SQLException
   */
  private ComputerMapper() {
  }

  /**
   * Méthode qui retourne l'instance unique de la classe ComputerMapper.
   * @return l'instance de la classe ComputerMapper
   * @throws SQLException SQLException
   */
  public static ComputerMapper getInstance() throws SQLException {
    if (ComputerMapper.instance == null) {
      ComputerMapper.instance = new ComputerMapper();
    }
    return ComputerMapper.instance;
  }

  /**
   * Récupère les DTOs.
   * @param computers les ordinateurs
   * @return computersTO les DTOs
   */
  public ArrayList<ComputerTO> getComputerData(ArrayList<Computer> computers) {
    return createComputerTO(computers);
  }

  /**
   * Crée les DTOs.
   * @param computers les ordinateurs
   * @return computersTO les DTOs
   */
  private ArrayList<ComputerTO> createComputerTO(ArrayList<Computer> computers) {
    ArrayList<ComputerTO> computersTO = new ArrayList<ComputerTO>();
    for (Computer computer : computers) {
      ComputerTO computerTO = new ComputerTO(computer);
      computersTO.add(computerTO);
    }
      return computersTO;
  }
}
