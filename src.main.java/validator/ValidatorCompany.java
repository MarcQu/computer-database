package validator;

import org.springframework.stereotype.Component;

import exception.EmptyNameException;
import exception.SpecialCharacterIdException;
import exception.SpecialCharacterNameException;

@Component
public class ValidatorCompany {
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
