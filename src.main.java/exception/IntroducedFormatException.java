package exception;

public class IntroducedFormatException extends DateFormatException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe IntroducedFormatException.
   */
  public IntroducedFormatException() {
    super("Le format de la date d'introduction n'est pas valide");
  }
}
