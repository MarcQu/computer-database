package validator;

import java.sql.SQLException;
import java.sql.Timestamp;

import exception.DatePrecedenceException;
import exception.DiscontinuedFormatException;
import exception.EmptyNameException;
import exception.IntroducedFormatException;
import exception.SpecialCharacterCompanyIdException;
import exception.SpecialCharacterDiscontinuedException;
import exception.SpecialCharacterIdException;
import exception.SpecialCharacterIntroducedException;
import exception.SpecialCharacterNameException;

public class ValidatorComputer {
  private static ValidatorComputer instance = null;

  /**
   * Constructeur vide privée classe Validator.
   */
  private ValidatorComputer() {
  }

  /**
   * Méthode qui retourne l'instance unique de la classe Validator.
   * @return l'instance de la classe Validator
   * @throws SQLException
   */
  public static ValidatorComputer getInstance() {
    if (ValidatorComputer.instance == null) {
      ValidatorComputer.instance = new ValidatorComputer();
    }
    return ValidatorComputer.instance;
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
   * Methode qui valide si le format de la date d'introduction est correct.
   * @param date la date
   * @throws IntroducedFormatException IntroducedFormatException
   */
  public void validateIntroducedFormat(String date) throws IntroducedFormatException {
    if (!"".equals(date) && !date.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
      throw new IntroducedFormatException();
    }
  }

  /**
   * Methode qui valide si le format de la d'interruption est correct.
   * @param date la date
   * @throws DiscontinuedFormatException DiscontinuedFormatException
   */
  public void validateDiscontinuedFormat(String date) throws DiscontinuedFormatException {
    if (!"".equals(date) && !date.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
      throw new DiscontinuedFormatException();
    }
  }

  /**
   * Methode qui valide si la date d'introduction est antérieur à la date d'interruption.
   * @param introduced la date d'introduction
   * @param discontinued la date d'interruption
   * @throws DatePrecedenceException DateException
   */
  public void validateDatePrecedence(String introduced, String discontinued) throws DatePrecedenceException {
    if (!"".equals(introduced) && !"".equals(discontinued) && Timestamp.valueOf(introduced).after(Timestamp.valueOf(discontinued))) {
      throw new DatePrecedenceException();
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

  /**
   * Methode qui valide si la date d'introduction ne possède pas de caractère spécial.
   * @param introduced la date d'introduction
   * @throws SpecialCharacterIntroducedException SpecialCharacterIntroducedException
   */
  public void validateSpecialCharaterIntroduced(String introduced) throws SpecialCharacterIntroducedException {
    if (introduced.matches(".*;.*") || introduced.matches(".*\".*") || introduced.matches(".*#.*")) {
      throw new SpecialCharacterIntroducedException();
    }
  }

  /**
   * Methode qui valide si la date d'introduction ne possède pas de caractère spécial.
   * @param discontinued la date d'interruption
   * @throws SpecialCharacterDiscontinuedException SpecialCharacterDiscontinuedException
   */
  public void validateSpecialCharaterDiscontinued(String discontinued) throws SpecialCharacterDiscontinuedException {
    if (discontinued.matches(".*;.*") || discontinued.matches(".*\".*") || discontinued.matches(".*#.*")) {
      throw new SpecialCharacterDiscontinuedException();
    }
  }

  /**
   * Methode qui valide si l'id de la compagnie ne possède pas de caractère spécial.
   * @param companyId l'id de la compagnie
   * @throws SpecialCharacterCompanyIdException SpecialCharacterCompanyIdException
   */
  public void validateSpecialCharaterCompanyId(String companyId) throws SpecialCharacterCompanyIdException {
    if (companyId.matches(".*;.*") || companyId.matches(".*\".*") || companyId.matches(".*#.*")) {
      throw new SpecialCharacterCompanyIdException();
    }
  }
}
