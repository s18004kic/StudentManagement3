package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import java.util.List;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception{
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service,times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception{
    String id = "111";
    mockMvc.perform(get("/student/{id}",id))
        .andExpect(status().isOk());

    verify(service,times(1)).searchStudent(id);
  }

  @Test
  void 受講生の登録が成功して空で返る() throws Exception {
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
      """
        {
            "student": {
                "name": "高橋次郎",
                "kanaName": "タカハシジロウ",
                "nickname": "ジロウ",
                "email": "test@example.com",
                "area": "神奈川",
                "age": "40",
                "sex": "男性",
                "remark": "",
                "telephone": "",
                "remark":""
            },
            "studentCourseList": [
                {
                    "courseName": "Javaコース"
                }
            ]
        }
        """
      ))
        .andExpect(status().isOk());

    verify(service,times(1)).registerStudent(any());
  }

  @Test
  void 受講生の更新が成功して空で返る() throws Exception {
    mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
              {
                  "student": {
                      "id": "23",
                      "name": "高橋次郎",
                      "kanaName": "タカハシジロウ",
                      "nickname": "ジロウ",
                      "email": "test@example.com",
                      "area": "神奈川",
                      "age": "40",
                      "sex": "男性",
                      "remark": "",
                      "telephone": "",
                      "remark":""
                  },
                  "studentCourseList": [
                      {
                          "id": "16",
                          "studentId": "23",
                          "courseName": "Javaコース",
                          "courseStartAt": "2025-01-19T10:15:23",
                          "courseEndAt": "2026-01-19T10:15:23"
                      }
                  ]
              }
              """
        ))
        .andExpect(status().isOk());

    verify(service,times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception{
    mockMvc.perform(get("/exception"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("現在のこのAPIは利用できません。古いURLとなっております。"));

  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと(){
    Student student = new Student();
    student.setId("1");
    student.setName("高橋公太");
    student.setKanaName("タカハシコウタ");
    student.setNickname("コウタ");
    student.setEmail("test@example.com");
    student.setArea("兵庫県");
    student.setSex("その他");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックにかかること(){
    Student student = new Student();
    student.setId("テストです。");
    student.setName("高橋公太");
    student.setKanaName("タカハシコウタ");
    student.setNickname("コウタ");
    student.setEmail("test@example.com");
    student.setArea("兵庫県");
    student.setSex("その他");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");
  }
}