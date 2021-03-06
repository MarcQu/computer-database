package validator;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import dto.ComputerTO;
import exception.DatePrecedenceException;
import exception.DiscontinuedFormatException;
import exception.EmptyNameException;
import exception.IntroducedFormatException;
import exception.SpecialCharacterCompanyIdException;
import exception.SpecialCharacterDiscontinuedException;
import exception.SpecialCharacterIdException;
import exception.SpecialCharacterIntroducedException;
import exception.SpecialCharacterNameException;
import model.Company;

@Component
public class ValidatorComputer {
  /**
   * Methode qui valide les attributs de la DTO.
   * @param computerTO la DTO de computer
   * @throws EmptyNameException EmptyNameException
   * @throws IntroducedFormatException IntroducedFormatException
   * @throws DiscontinuedFormatException DiscontinuedFormatException
   * @throws DatePrecedenceException DatePrecedenceException
   * @throws SpecialCharacterNameException SpecialCharacterNameException
   * @throws SpecialCharacterIntroducedException SpecialCharacterIntroducedException
   * @throws SpecialCharacterDiscontinuedException SpecialCharacterDiscontinuedException
   * @throws SpecialCharacterCompanyIdException SpecialCharacterCompanyIdException
   */
  public void validate(ComputerTO computerTO) throws EmptyNameException, IntroducedFormatException, DiscontinuedFormatException, DatePrecedenceException, SpecialCharacterNameException, SpecialCharacterIntroducedException, SpecialCharacterDiscontinuedException, SpecialCharacterCompanyIdException {
    validateEmptyName(computerTO.getName());
    validateIntroducedFormat(computerTO.getIntroduced());
    validateDiscontinuedFormat(computerTO.getDiscontinued());
    validateDatePrecedence(computerTO.getIntroduced(), computerTO.getDiscontinued());
    validateSpecialCharaterName(computerTO.getName());
    validateSpecialCharaterIntroduced(computerTO.getIntroduced());
    validateSpecialCharaterDiscontinued(computerTO.getDiscontinued());
    validateSpecialCharaterCompanyId(computerTO.getCompany());
  }

  /**
   * Methode qui valide si le champ name est non vide.
   * @param name le nom
   * @throws EmptyNameException EmptyNameException
   */
  private void validateEmptyName(String name) throws EmptyNameException {
    if ("".equals(name)) {
      throw new EmptyNameException();
    }
  }

  /**
   * Methode qui valide si le format de la date d'introduction est correct.
   * @param date la date
   * @throws IntroducedFormatException IntroducedFormatException
   */
  private void validateIntroducedFormat(String date) throws IntroducedFormatException {
    if (!"".equals(date) && !date.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
      throw new IntroducedFormatException();
    }
  }

  /**
   * Methode qui valide si le format de la d'interruption est correct.
   * @param date la date
   * @throws DiscontinuedFormatException DiscontinuedFormatException
   */
  private void validateDiscontinuedFormat(String date) throws DiscontinuedFormatException {
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
  private void validateDatePrecedence(String introduced, String discontinued) throws DatePrecedenceException {
    if (!"".equals(introduced) && !"".equals(discontinued) && Timestamp.valueOf(introduced).after(Timestamp.valueOf(discontinued))) {
      throw new DatePrecedenceException();
    }
  }

  /**
   * Methode qui valide si le nom ne possède pas de caractère spécial.
   * @param name le nom
   * @throws SpecialCharacterNameException SpecialCharacterNameException
   */
  private void validateSpecialCharaterName(String name) throws SpecialCharacterNameException {
    if (name.matches(".*;.*") || name.matches(".*\".*") || name.matches(".*#.*")) {
      throw new SpecialCharacterNameException();
    }
  }

  /**
   * Methode qui valide si la date d'introduction ne possède pas de caractère spécial.
   * @param introduced la date d'introduction
   * @throws SpecialCharacterIntroducedException SpecialCharacterIntroducedException
   */
  private void validateSpecialCharaterIntroduced(String introduced) throws SpecialCharacterIntroducedException {
    if (introduced.matches(".*;.*") || introduced.matches(".*\".*") || introduced.matches(".*#.*")) {
      throw new SpecialCharacterIntroducedException();
    }
  }

  /**
   * Methode qui valide si la date d'introduction ne possède pas de caractère spécial.
   * @param discontinued la date d'interruption
   * @throws SpecialCharacterDiscontinuedException SpecialCharacterDiscontinuedException
   */
  private void validateSpecialCharaterDiscontinued(String discontinued) throws SpecialCharacterDiscontinuedException {
    if (discontinued.matches(".*;.*") || discontinued.matches(".*\".*") || discontinued.matches(".*#.*")) {
      throw new SpecialCharacterDiscontinuedException();
    }
  }

  /**
   * Methode qui valide si l'id de la compagnie ne possède pas de caractère spécial.
   * @param company la compagnie
   * @throws SpecialCharacterCompanyIdException SpecialCharacterCompanyIdException
   */
  private void validateSpecialCharaterCompanyId(Company company) throws SpecialCharacterCompanyIdException {
    if (company != null) {
      if (Integer.toString(company.getId()).matches(".*;.*") || Integer.toString(company.getId()).matches(".*\".*") || Integer.toString(company.getId()).matches(".*#.*")) {
        throw new SpecialCharacterCompanyIdException();
      }
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
}
