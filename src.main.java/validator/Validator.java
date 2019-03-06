package validator;

import java.sql.SQLException;
import java.sql.Timestamp;

import exception.DateException;
import exception.EmptyNameException;

public class Validator {
  private static Validator instance = null;

  /**
   * Constructeur vide privée classe Validator.
   */
  private Validator() {
  }

  /**
   * Méthode qui retourne l'instance unique de la classe Validator.
   * @return l'instance de la classe Validator
   * @throws SQLException
   */
  public static Validator getInstance() {
    if (Validator.instance == null) {
      Validator.instance = new Validator();
    }
    return Validator.instance;
  }

  /**
   * Method qui valide si le champ name est non vide.
   * @param name le nom
   * @throws EmptyNameException EmptyNameException
   */
  public void validateName(String name) throws EmptyNameException {
    if ("".equals(name)) {
      throw new EmptyNameException("Le nom ne doit pas être vide");
    }
  }

  /**
   * Method qui valide si la date d'introduction est antérieur à la date d'interruption.
   * @param introduced la date d'introduction
   * @param discontinued la date d'interruption
   * @throws DateException DateException
   */
  public void validateDate(String introduced, String discontinued) throws DateException {
    if (!"".equals(introduced) && !"".equals(discontinued) && Timestamp.valueOf(introduced).after(Timestamp.valueOf(discontinued))) {
      throw new DateException("La date d'introduction doit être antérieur à la date d'interruption");
    }
  }
}
