package raisetech.StudentManagement.exception.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.StudentManagement.exception.TestException;

//ここでどのクラスを使用するのかを記述
@RestControllerAdvice
public class TestExceptionHandler extends Exception {
@ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(
      raisetech.StudentManagement.exception.TestException ex) {
      System.out.println(ex.toString());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
