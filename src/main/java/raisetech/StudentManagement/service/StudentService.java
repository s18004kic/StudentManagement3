package raisetech.StudentManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

  private StudentRepository repository;
  //private List<Student> students;
  //private List<Student> students = new ArrayList<>();

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {//search30{ //getStudentList()
    //return repository.searchStudent();
    //検索処理 repository.search();return students.stream()
    //List<Student> filteredStudents = students.stream()
    //    .filter(student -> students.get >= 30 && student.getAge() < 40)
    //    .collect(Collectors.toList());
    //return filteredStudents;

    return repository.search().stream()
        .filter(student -> student.getAge() >= 0 && student.getAge() < 100)
        .collect(Collectors.toList());
  }  //ここで何からの処理を行う

  public List<StudentsCourses> searchStudentCourseList() {
    // Javaコースをフィルタリングして返す
    return repository.findAll(); //.stream()
        //.filter(course -> "Javaコース".equals(course.getCourseName()))
        //.collect(Collectors.toList());
  }
}


//@Service
//public class StudentService {
//  private final StudentRepository repository;
//
//  public StudentService(StudentRepository repository) {
//    this.repository = repository;
//  }
//
//  public List<Student> getActiveStudents() {
//    return repository.findActiveStudents();
//  }
//
//  public void updateRemark(Long id, String remark) {
//    Student student = repository.findById(id).orElseThrow();
//    student.setRemark(remark);
//    repository.save(student);
//  }
//}



  //public List<StudentsCourses>searchStudentCourseList(){ //getStudentsCourseList() {earchStudentCourseJava(){
  //  //return repository.searchStudentCourseJava();
  //      return students.stream()
  //      .filter(student -> "Javaコース".equals(student.getCourse()))
  //      .collect(Collectors.toList());
  //  return repository.findAll();
  //}
//}

