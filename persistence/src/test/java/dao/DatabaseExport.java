package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DatabaseExport {

  public static void main(String[] args) throws SQLException, DatabaseUnitException, FileNotFoundException, IOException {
    // database connection
    Properties prp = new Properties();
    String path = "D:\\Eclipse-workspace\\computer-database\\webapp\\src\\main\\resources\\dao.properties";
    InputStream input = new FileInputStream(path);
    prp.load(input);
    Connection conn = DriverManager.getConnection(prp.getProperty("url"), prp.getProperty("login"), prp.getProperty("password"));
    IDatabaseConnection connection = new DatabaseConnection(conn);
    
    // partial database export
    QueryDataSet partialDataSet = new QueryDataSet(connection);
    partialDataSet.addTable("company", "SELECT * FROM company");
    partialDataSet.addTable("computer", "SELECT * FROM computer WHERE introduced AND discontinued IS NOT NULL");
    FlatXmlDataSet.write(partialDataSet, new FileOutputStream("D:\\Eclipse-workspace\\computer-database\\persistence\\src\\test\\java\\dao\\dataSet.xml"));
    
    conn.close();
    connection.close();
  }
}
