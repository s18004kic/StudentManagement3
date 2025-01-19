package raisetech.StudentManagement.exception.Handler;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;

//@ExceptionHandler(raisetech.StudentManagement.exception.TestException.class)
public class TestException extends Exception {

  public ResponseEntity<String> handleTestException(
      raisetech.StudentManagement.exception.TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList()
      throws raisetech.StudentManagement.exception.TestException {
    throw new raisetech.StudentManagement.exception.TestException(
        "現在のこのAPIは利用できません。URLは「studentList」ではなく「students」を利用ください。");
  }
}
