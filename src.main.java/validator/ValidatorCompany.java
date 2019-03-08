package validator;

import java.sql.SQLException;

import exception.EmptyNameException;
import exception.SpecialCharacterIdException;
import exception.SpecialCharacterNameException;

public class ValidatorCompany {
  private static ValidatorCompany instance = null;

  /**
   * Constructeur vide privée classe Validator.
   */
  private ValidatorCompany() {
  }

  /**
   * Méthode qui retourne l'instance unique de la classe Validator.
   * @return l'instance de la classe Validator
   * @throws SQLException
   */
  public static ValidatorCompany getInstance() {
    if (ValidatorCompany.instance == null) {
      ValidatorCompany.instance = new ValidatorCompany();
    }
    return ValidatorCompany.instance;
  }

  /**
   * Methode qui valide si le champ name est non vide.
   * @param name le nom
   * @throws EmptyNameException EmptyNameException
   */
  public void validateEmptyName(String name) throws EmptyNameException {
    if ("".equals(name)) {
      throw new EmptyNameException();
    }
  }

  /**
   * Methode qui valide si un champ ne possède pas de caractère spécial.
   * @param id le id
   * @throws SpecialCharacterIdException SpecialCharacterIdException
   */
  public void validateSpecialCharaterId(String id) throws SpecialCharacterIdException {
    if (id.matches(".*;.*") || id.matches(".*\".*") || id.matches(".*#.*")) {
      throw new SpecialCharacterIdException();
    }
  }

  /**
   * Methode qui valide si le nom ne possède pas de caractère spécial.
   * @param name le nom
   * @throws SpecialCharacterNameException SpecialCharacterNameException
   */
  public void validateSpecialCharaterName(String name) throws SpecialCharacterNameException {
    if (name.matches(".*;.*") || name.matches(".*\".*") || name.matches(".*#.*")) {
      throw new SpecialCharacterNameException();
    }
  }
}
