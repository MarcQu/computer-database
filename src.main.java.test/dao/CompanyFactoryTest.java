package dao;

import static org.junit.Assert.assertEquals;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import model.Company;

public class CompanyFactoryTest {
  private CompanyFactory companyFactory;
  private DAOFactory daoFactory;
  private static final String URL = "jdbc:mysql://localhost:3306/computer-database-test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B1";

  /**
   * Initialise la connexion à la base de données de test.
   * @throws SQLException SQLException
   */
  @Before
  public void init() throws SQLException {
    Properties prp = new Properties();
    prp.put("user", "root");
    prp.put("password", "network");
    this.daoFactory = Mockito.mock(DAOFactory.class);
    Mockito.when(this.daoFactory.getConnection()).thenReturn(DriverManager.getConnection(URL, prp));
    this.companyFactory = CompanyFactory.getInstance();
  }

  /**
   * Test la liste des companies avec limit.
   * @throws SQLException SQLException
   */
  @Test
  public void listCompaniesTest() throws SQLException {
    ArrayList<Company> companies = this.companyFactory.listCompanies(10, 0, "", "");
    assertEquals(10, companies.size());
  }

  /**
   * Test la liste de toutes les companies.
   * @throws SQLException SQLException
   */
  @Test
  public void listCompaniesAllTest() throws SQLException {
    ArrayList<Company> companies = this.companyFactory.listCompaniesAll();
    assertEquals(this.companyFactory.countCompanies(null), companies.size());
  }
}
