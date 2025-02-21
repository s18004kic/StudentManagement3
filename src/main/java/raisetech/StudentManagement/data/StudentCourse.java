package raisetech.StudentManagement.data;

//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import java.time.LocalDateTime;
//import java.util.Objects;
//import lombok.Getter;
//import lombok.Setter;
//
//@Schema(description = "受講生コース情報")
//@Getter
//@Setter
//public class StudentCourse {
//
//  @Pattern(regexp = "^\\d+$")
//  private String id;
//
//  @Pattern(regexp = "^\\d+$")
//  private String studentId;
//
//  @NotBlank
//  private String courseName;
//
//  private LocalDateTime courseStartAt;
//  private LocalDateTime courseEndAt;
//  private String status; // 仮申込・本申込・受講中・受講終了
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    StudentCourse that = (StudentCourse) o;
//    return Objects.equals(id, that.id) &&
//        Objects.equals(studentId, that.studentId) &&
//        Objects.equals(courseName, that.courseName) &&
//        Objects.equals(courseStartAt, that.courseStartAt) &&
//        Objects.equals(courseEndAt, that.courseEndAt) &&
//        Objects.equals(status, that.status);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(id, studentId, courseName, courseStartAt, courseEndAt, status);
//  }
//}
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@AllArgsConstructor  // これで全フィールドのコンストラクタを自動生成
@EqualsAndHashCode
public class StudentCourse {

  @Pattern(regexp = "^\\d+$")
  private String id;

  @Pattern(regexp = "^\\d+$")
  private String studentId;

  @NotBlank
  private String courseName;

  private LocalDateTime courseStartAt;
  private LocalDateTime courseEndAt;
  private String status; // 仮申込・本申込・受講中・受講終了

  // 🌟 引数なしのデフォルトコンストラクタ（必要なら追加）
  public StudentCourse() {
  }


  // 🌟 toString() のオーバーライド（デバッグ用）
  @Override
  public String toString() {
    return "StudentCourse{" +
        "Id='" + id + '\'' +
        "studentId='" + studentId + '\'' +
        ", courseName='" + courseName + '\'' +
        ", courseStartAt=" + courseStartAt +
        ", courseEndAt=" + courseEndAt +
        ", status='" + status + '\'' +
        '}';
  }

  // ✅ IDなしのコンストラクタ（テスト用など）
  public StudentCourse(String studentId, String courseName,
      LocalDateTime courseStartAt, LocalDateTime courseEndAt, String status) {
    this(null, studentId, courseName, courseStartAt, courseEndAt, status);
  }
}