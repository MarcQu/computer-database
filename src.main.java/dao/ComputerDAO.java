package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

import mapper.ComputerMapper;
import model.Computer;

@Repository
public class ComputerDAO {
  private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static final String COUNT_ALL = "SELECT COUNT(id) AS rowcount FROM computer";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM computer WHERE name like :search";
  private static final String SHOW = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id = ?";
  private static final String CREATE = "INSERT INTO computer(name, introduced, discontinued, company_id) values(?, ?, ?, ?)";
  private static final String LIST_ASC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String LIST_DESC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String SEARCH_ASC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL AND computer.name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String SEARCH_DESC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL AND computer.name LIKE ? ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String DELETE = "DELETE FROM computer WHERE id = ?";
  private Logger logger;
  @Autowired
  private ComputerMapper mapper;

  @Autowired
  private HikariDataSource dataSource;

  /**
   * ComputerFactory contient les méthodes spécifiques à la table computer.
   * @throws SQLException SQLException
   */
  @Autowired
  private ComputerDAO() throws SQLException {
    this.logger = LoggerFactory.getLogger(ComputerDAO.class);
  }

  /**
   * Retourne le nombre de lignes dans la table computer.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countComputers(String search) throws SQLException {
    NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
    MapSqlParameterSource params = new MapSqlParameterSource();
    int nombre = 0;
    if (search == null || "".equals(search)) {
      nombre = template.queryForObject(COUNT_ALL, params, Integer.class);
    } else {
      params.addValue("search", new StringBuilder("%").append(search).append("%").toString());
      nombre = template.queryForObject(COUNT, params, Integer.class);
    }
    return nombre;
  }

  /**
   * Liste quelques ordinateurs contenus dans la table computer.
   * @param nombre nombre de résultat à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Computer> listComputers(int nombre, int offset, String search, String sort) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    List<Computer> computers = new ArrayList<Computer>();

    if (search == null || "".equals(search)) {
      if ("desc".equals(sort)) {
        computers = template.query(LIST_DESC, new Object[] {nombre, offset}, mapper);
      } else {
        computers = template.query(LIST_ASC, new Object[] {nombre, offset}, mapper);
      }
    } else {
      if ("desc".equals(sort)) {
        computers = template.query(SEARCH_DESC, new Object[] {new StringBuilder("%").append(search).append("%").toString(), new StringBuilder("%").append(search).append("%").toString(), nombre, offset}, mapper);
      } else {
        computers = template.query(SEARCH_ASC, new Object[] {new StringBuilder("%").append(search).append("%").toString(), new StringBuilder("%").append(search).append("%").toString(), nombre, offset}, mapper);
      }
    }
    return computers;
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param computer l'ordinateur à afficher
   * @return computers la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Computer> showComputerDetails(Computer computer) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    return template.query(SHOW, new Object[] {computer.getId(), computer.getId()}, mapper);
  }

  /**
   * Ajoute un ordinateur dans la table computer.
   * @param computer l'ordinateur à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   */
  public void createComputer(Computer computer) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    Timestamp introduced = null;
    Timestamp discontinued = null;
    Integer companyId = null;

    if (computer.getIntroduced() != null) {
      introduced = Timestamp.valueOf(computer.getIntroduced().atStartOfDay());
    }
    if (computer.getDiscontinued() != null) {
      discontinued = Timestamp.valueOf(computer.getDiscontinued().atStartOfDay());
    }
    if (computer.getCompany() != null) {
      companyId = computer.getCompany().getId();
    }
    template.update(CREATE, computer.getName(), introduced, discontinued, companyId);
  }

  /**
   * Met à jour un ordinateur dans la table computer.
   * @param computer l'ordinateur à mettre à jour
   * @param champs       les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   */
  public void updateComputer(Computer computer, ArrayList<String> champs) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    StringBuilder query = new StringBuilder("UPDATE computer SET ");
    for (int i = 0; i < champs.size() - 1; i++) {
      query.append(champs.get(i)).append(" = ?, ");
    }
    query.append(champs.get(champs.size() - 1)).append(" = ? WHERE id = ?");

    Timestamp introduced = null;
    Timestamp discontinued = null;
    Integer companyId = null;

    if (computer.getIntroduced() != null) {
      introduced = Timestamp.valueOf(computer.getIntroduced().atStartOfDay());
    }
    if (computer.getDiscontinued() != null) {
      discontinued = Timestamp.valueOf(computer.getDiscontinued().atStartOfDay());
    }
    if (computer.getCompany() != null) {
      companyId = computer.getCompany().getId();
    }
    template.update(query.toString(), computer.getName(), introduced, discontinued, companyId, computer.getId());
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param computer l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void deleteComputer(Computer computer) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    template.update(DELETE, computer.getId());
  }
}