package raisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@RestController
public class StudentController {

  /** 受講生サービス */
  private StudentService service;
  /** 受講生コンバーター　*/
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   * @return　受講生一覧（全件）
   */
  @GetMapping("/studentList")
  public String getStudentList(Model model){  //List<StudentDetail>
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   * @param id　受講生ID
   * @return　受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id){
    return service.searchStudent(id);
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCourseList(){
    return service.searchStudentCourseList();//repository.findAll();//findAllとは別にStudentsCoursesを入れるのもＯＫ
  }

  //@GetMapping("/newStudent")
  //public String newStudent(Model model){
  //  StudentDetail studentDetail = new StudentDetail();
  //  studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
  //  model.addAttribute("studentDetail", studentDetail);
  //  return "registerStudent";
  //}

  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail){
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}

