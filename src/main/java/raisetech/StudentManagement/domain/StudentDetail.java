package raisetech.StudentManagement.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;

  //public void setStudent(Student student) {
  //  this.student = student;
  //}
//
  //public Student getStudent() {
  //  return student;
  //}

  //public void setStudentsCourses(List<StudentsCourses> studentsCourses) {
  //  this.studentsCourses = studentsCourses;
  //}
//
  //public List<StudentsCourses> getStudentsCourses() {
  //  return studentsCourses;
  //}
}
