package exception;

public class DateFormatException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe DateFormatException.
   * @param message le message affiché quand le format de la date n'est pas correct
   */
  public DateFormatException(String message) {
    super(message);
  }
}