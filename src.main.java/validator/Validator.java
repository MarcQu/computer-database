package validator;

import java.sql.SQLException;
import java.sql.Timestamp;

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
   * @return valid le boolean
   */
  public boolean validateName(String name) {
    boolean valid = false;
    if ("".equals(name)) {
      valid = true;
    }
    return valid;
  }

  /**
   * Method qui valide si la date d'introduction est antérieur à la date d'interruption.
   * @param introduced la date d'introduction
   * @param discontinued la date d'interruption
   * @return valid le boolean
   */
  public boolean validateDate(String introduced, String discontinued) {
    boolean valid = false;
    if (!"".equals(introduced) && !"".equals(discontinued) && Timestamp.valueOf(introduced).after(Timestamp.valueOf(discontinued))) {
      valid = true;
    }
    return valid;
  }
}
