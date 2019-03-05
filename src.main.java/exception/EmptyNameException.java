package exception;

public class EmptyNameException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe EmptyNameException.
   * @param message le message retourné lorsque le nom est vide
   */
  public EmptyNameException(String message) {
    super(message);
  }
}
