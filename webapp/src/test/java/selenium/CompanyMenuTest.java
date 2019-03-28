package selenium;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CompanyMenuTest {
  private String url;
  private WebDriver driver;

  /**
   * Initialise selenium pour chrome.
   */
  @Before
  public void init() {
    System.setProperty("webdriver.chrome.driver", "D:\\Eclipse-workspace\\driver\\chromedriver.exe");
    driver = new ChromeDriver();
    url = "http://localhost:8080/webapp/CompanyMenu?nombre=10&page=1";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  /**
   * Test l'affichage de la pagination.
   */
  @Test
  public void paginationTest() {
    driver.get(url);
    assertEquals(10, driver.findElements(By.xpath("//tr[@id='companiesList']")).size());
    driver.findElement(By.cssSelector("a[role='button'][href='/Computer-database/CompanyMenu?nombre=50&page=1']")).click();
    assertEquals(42, driver.findElements(By.xpath("//tr[@id='companiesList']")).size());
    driver.findElement(By.cssSelector("a[role='button'][href='/Computer-database/CompanyMenu?nombre=100&page=1']")).click();
    assertEquals(42, driver.findElements(By.xpath("//tr[@id='companiesList']")).size());
  }
}
