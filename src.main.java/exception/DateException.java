package exception;

public class DateException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe DateException.
   * @param message le message retourné lorsque la date d'interruption est antérieur à la date d'introduction
   */
  public DateException(String message) {
    super(message);
  }
}
