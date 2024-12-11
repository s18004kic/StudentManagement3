package raisetech.StudentManagement.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public LocalDateTime getCourseStartAt() {
    return courseStartAt;
  }

  public void setCourseStartAt(LocalDateTime courseStartAt) {
    this.courseStartAt = courseStartAt;
  }

  public LocalDateTime getCourseEndAt() {
    return courseEndAt;
  }

  public void setCourseEndAt(LocalDateTime courseEndAt) {
    this.courseEndAt = courseEndAt;
  }

  private String id;
  private String studentId;
  private String courseName;
  private LocalDateTime courseStartAt;
  private LocalDateTime courseEndAt;
}
