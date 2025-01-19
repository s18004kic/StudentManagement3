package raisetech.StudentManagement.exception;

public class TestException extends Exception {

  public TestException() {
    super();
  }

  //例外のメッセージ
  public TestException(String message) {
    super(message);
  }

  //例外の原因
  public TestException(String message, Throwable cause) {
    super(message, cause);
  }

  public TestException(Throwable cause) {
    super(cause);
  }
}
