package exception;

public class EmptyNameException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe EmptyNameException.
   */
  public EmptyNameException() {
    super("Le nom ne doit pas être vide");
  }
}
