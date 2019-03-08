package dao;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ComputerFactoryTest {
  private ComputerDAOFactory computerFactory;
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
    this.computerFactory = ComputerDAOFactory.getInstance();
  }

  /**
   * Test la liste des ordinateurs avec limit.
   * @throws SQLException SQLException
   */
  @Test
  public void listComputersTest() throws SQLException {
  }

  /**
   * Test la liste de tous les ordinateurs.
   * @throws SQLException SQLException
   */
  @Test
  public void listComputersAllTest() throws SQLException {
  }

  /**
   * Test l'affichage des informations d'un ordinateur.
   * @throws SQLException SQLException
   */
  @Test
  public void showComputerDetailsTest() throws SQLException {
  }

  /**
   * Test la mise à jour des informations d'un ordinateur.
   * @throws SQLException SQLException
   */
  @Test
  public void updateComputerTest() throws SQLException {
  }

  /**
   * Test la création et la suppression d'un ordinateur.
   * @throws SQLException SQLException
   */
  @Test
  public void createDeleteComputer() throws SQLException {
  }
}