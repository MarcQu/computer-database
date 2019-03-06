package mapper;

import java.sql.SQLException;
import java.sql.Timestamp;
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
  public ArrayList<ComputerTO> getComputerTO(ArrayList<Computer> computers) {
    return createComputerTO(computers);
  }

  /**
   * Récupère les computers.
   * @param computersTO les DTOs
   * @return computers les ordinateurs
   */
  public ArrayList<Computer> getComputer(ArrayList<ComputerTO> computersTO) {
    return createComputer(computersTO);
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

  /**
   * Crée les computers.
   * @param computersTO les DTOs
   * @return computers les ordinateurs
   */
  private ArrayList<Computer> createComputer(ArrayList<ComputerTO> computersTO) {
    ArrayList<Computer> computers = new ArrayList<Computer>();
    for (ComputerTO computerTO : computersTO) {
      Computer computer = new Computer();
      computer.setId(computerTO.getId());
      computer.setName(computerTO.getName());
      computer.setIntroduced(Timestamp.valueOf(computerTO.getIntroduced()));
      computer.setDiscontinued(Timestamp.valueOf(computerTO.getDiscontinued()));
      computer.setCompany(computerTO.getCompany());
      computersTO.add(computerTO);
    }
      return computers;
  }
}
