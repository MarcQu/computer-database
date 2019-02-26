package selenium;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateComputerTest {
  private String url;
  private WebDriver driver;

  /**
   * Initialise selenium pour chrome.
   */
  @Before
  public void init() {
    System.setProperty("webdriver.chrome.driver", "D:\\Eclipse-workspace\\driver\\chromedriver.exe");
    driver = new ChromeDriver();
    url = "http://localhost:8080/Computer-database/CreateComputer";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  /**
   * Test l'affichage du message d'erreur quand le nom est vide.
   */
  @Test
  public void errorNameTest() {
    driver.get(url);
    driver.findElement(By.cssSelector("input[class='btn btn-primary']")).click();
    String errorName = driver.findElement(By.cssSelector("label[id='errorName']")).getAttribute("innerText");
    assertEquals("Le nom ne doit pas être vide", errorName);
  }

  /**
   * Test l'affichage du message d'erreur quand la date d'interruption est antérieur à la date d'introduction.
   */
  @Test
  public void errorDateTest() {
    driver.get(url);
    driver.findElement(By.cssSelector("input[id='introduced']")).sendKeys("10-10-1990");
    driver.findElement(By.cssSelector("input[id='discontinued']")).sendKeys("01-01-1990");
    driver.findElement(By.cssSelector("input[class='btn btn-primary']")).click();
    String errorDate = driver.findElement(By.cssSelector("label[id='errorDate']")).getAttribute("innerText");
    assertEquals("La date d'introduction doit être antérieur à la date d'interruption", errorDate);
  }

  /**
   * Test l'affichage du message de succès de la création.
   */
  @Test
  public void successCreationTest() {
    driver.get(url);
    driver.findElement(By.cssSelector("input[id='computerName']")).sendKeys("test");
    driver.findElement(By.cssSelector("input[id='introduced']")).sendKeys("01-01-1990");
    driver.findElement(By.cssSelector("input[id='discontinued']")).sendKeys("10-10-1990");
    driver.findElement(By.cssSelector("option[value='1']")).click();
    driver.findElement(By.cssSelector("input[class='btn btn-primary']")).click();
    String success = driver.findElement(By.cssSelector("label[id='success']")).getAttribute("innerText");
    assertEquals("Succès de la création", success);
  }
}
