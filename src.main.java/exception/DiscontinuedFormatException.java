package exception;

public class DiscontinuedFormatException extends DateFormatException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe DiscontinuedFormatException.
   */
  public DiscontinuedFormatException() {
    super("Le format de la date d'interruption n'est pas valide");
  }
}
