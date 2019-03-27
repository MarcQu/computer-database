package exception;

public class DatePrecedenceException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe DateException.
   */
  public DatePrecedenceException() {
    super("La date d'introduction doit être antérieur à la date d'interruption");
  }
}
