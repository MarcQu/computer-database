package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import dto.ComputerTO;
import model.Company;
import model.Computer;

@Component
public class ComputerMapper implements RowMapper<Computer> {
  /**
   * Récupère les DTOs.
   * @param computers les ordinateurs
   * @return computersTO les DTOs
   */
  public List<ComputerTO> getComputerTO(List<Computer> computers) {
    return createComputerTO(computers);
  }

  /**
   * Récupère les computers.
   * @param computerTO la DTO
   * @return computer l'ordinateur
   */
  public Computer getComputer(ComputerTO computerTO) {
    return createComputer(computerTO);
  }

  /**
   * Crée les DTOs.
   * @param computers les ordinateurs
   * @return computersTO les DTOs
   */
  private List<ComputerTO> createComputerTO(List<Computer> computers) {
    ArrayList<ComputerTO> computersTO = new ArrayList<ComputerTO>();
    for (Computer computer : computers) {
      ComputerTO computerTO = new ComputerTO(computer);
      computersTO.add(computerTO);
    }
      return computersTO;
  }

  /**
   * Crée les computers.
   * @param computerTO la DTO
   * @return computer l'ordinateur
   */
  private Computer createComputer(ComputerTO computerTO) {
      Computer computer = new Computer();
      Optional<String> optionalId = Optional.ofNullable(computerTO.getId());
      Optional<String> optionalName = Optional.ofNullable(computerTO.getName());
      Optional<String> optionalIntroduced = Optional.ofNullable(computerTO.getIntroduced());
      Optional<String> optionalDiscontinued = Optional.ofNullable(computerTO.getDiscontinued());
      Optional<Company> optionalCompany = Optional.ofNullable(computerTO.getCompany());
      if (optionalId.isPresent() && !"".equals(computerTO.getId())) {
        computer.setId(Integer.parseInt(computerTO.getId()));
      }
      if (optionalName.isPresent() && !"".equals(computerTO.getName())) {
        computer.setName(computerTO.getName());
      }
      if (optionalIntroduced.isPresent() && !"".equals(computerTO.getIntroduced())) {
        computer.setIntroduced(Timestamp.valueOf(computerTO.getIntroduced()).toLocalDateTime().toLocalDate());
      }
      if (optionalDiscontinued.isPresent() && !"".equals(computerTO.getDiscontinued())) {
        computer.setDiscontinued(Timestamp.valueOf(computerTO.getDiscontinued()).toLocalDateTime().toLocalDate());
      }
      if (optionalCompany.isPresent() && !"".equals(computerTO.getCompany().getId().toString()) && !"".equals(computerTO.getCompany().getName())) {
        computer.setCompany(computerTO.getCompany());
      }
      return computer;
  }

  @Override
  public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
    Computer computer = new Computer();
    computer.setId(rs.getInt("id"));
    computer.setName(rs.getString("name"));
    if (rs.getString("introduced") != null && !"0000-00-00 00:00:00".equals(rs.getString("introduced"))) {
      computer.setIntroduced(Timestamp.valueOf(rs.getString("introduced")).toLocalDateTime().toLocalDate());
    }
    if (rs.getString("discontinued") != null && !"0000-00-00 00:00:00".equals(rs.getString("discontinued"))) {
      computer.setDiscontinued(Timestamp.valueOf(rs.getString("discontinued")).toLocalDateTime().toLocalDate());
    }

    Company company = new Company();
    company.setId(rs.getInt("company_id"));
    company.setName(rs.getString("company_name"));
    computer.setCompany(company);
    return computer;
  }
}
