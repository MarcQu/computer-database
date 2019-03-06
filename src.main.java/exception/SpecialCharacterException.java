package exception;

public class SpecialCharacterException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe SpecialCharacterException.
   * @param message le message retourné lorsqu'un caractère spécial est détécté
   */
  public SpecialCharacterException(String message) {
    super(message);
  }
}