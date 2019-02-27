package dao;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import model.Computer;

public class ComputerFactoryTest {
  private ComputerFactory computerFactory;
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
    Mockito.when(this.daoFactory.getConnection()).thenReturn(DriverManager.getConnection(URL, prp));    this.computerFactory = ComputerFactory.getInstance();
  }

  /**
   * Test la liste des ordinateurs avec limit.
   * @throws SQLException SQLException
   */
  @Test
  public void listComputersTest() throws SQLException {
    ArrayList<Computer> computers = this.computerFactory.listComputers(10, 0);
    assertEquals(10, computers.size());
  }

  /**
   * Test la liste de tous les ordinateurs.
   * @throws SQLException SQLException
   */
  @Test
  public void listComputersAllTest() throws SQLException {
    ArrayList<String> champs = new ArrayList<String>();
    champs.add("id");
    champs.add("name");
    ArrayList<Computer> computers = this.computerFactory.listComputersAll(champs);
    assertEquals(this.computerFactory.countComputers(), computers.size());
  }

  /**
   * Test l'affichage des informations d'un ordinateur.
   * @throws SQLException SQLException
   */
  @Test
  public void showComputerDetailsTest() throws SQLException {
    ArrayList<Computer> computers = this.computerFactory.showComputerDetails("5");
    Computer computer = computers.get(0);
    assertEquals(5, (int) computer.getId());
    assertEquals("CM-5", computer.getName());
    assertEquals(new Date(Timestamp.valueOf("1991-01-01 00:00:00").getTime()), computer.getIntroduced());
    assertEquals(null, computer.getDiscontinued());
    assertEquals(2, (int) computer.getCompany().getId());
  }

  /**
   * Test la mise à jour des informations d'un ordinateur.
   * @throws SQLException SQLException
   */
  @Test
  public void updateComputerTest() throws SQLException {
    ArrayList<String> champs = new ArrayList<String>();
    champs.add("introduced");

    this.computerFactory.updateComputer("1", "", "1991-01-01 00:00:00", "", "", champs);
    ArrayList<Computer> computers = this.computerFactory.showComputerDetails("1");
    Computer computer = computers.get(0);
    assertEquals(new Date(Timestamp.valueOf("1991-01-01 00:00:00").getTime()), computer.getIntroduced());

    this.computerFactory.updateComputer("1", "", "1990-10-10 00:00:00", "", "", champs);
    computers = this.computerFactory.showComputerDetails("1");
    computer = computers.get(0);
    assertEquals(new Date(Timestamp.valueOf("1990-10-10 00:00:00").getTime()), computer.getIntroduced());
  }

  /**
   * Test la création et la suppression d'un ordinateur.
   * @throws SQLException SQLException
   */
  @Test
  public void createDeleteComputer() throws SQLException {
    int expectedCount = this.computerFactory.countComputers() + 1;
    this.computerFactory.createComputer("test", "", "", "");
    assertEquals(expectedCount, this.computerFactory.countComputers());

    expectedCount = this.computerFactory.countComputers() - 1;
    this.computerFactory.deleteComputer("");
    assertEquals(expectedCount, this.computerFactory.countComputers());
  }
}