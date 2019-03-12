package dao;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class CompanyFactoryTest {
  private CompanyDAOFactory companyFactory;
  private DAOFactory daoFactory;
  private static final String URL = "jdbc:mysql://localhost:3306/computer-database-test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B1";

  /**
   * Initialise la connexion à la base de données de test.
   * @throws SQLException SQLException
   */
  @Before
  public void init() throws SQLException {
  }

  /**
   * Test la liste des companies avec limit.
   * @throws SQLException SQLException
   */
  @Test
  public void listCompaniesTest() throws SQLException {
  }

  /**
   * Test la liste de toutes les companies.
   * @throws SQLException SQLException
   */
  @Test
  public void listCompaniesAllTest() throws SQLException {
  }
}
