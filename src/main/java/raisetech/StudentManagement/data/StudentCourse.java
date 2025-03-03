package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
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

  // IDなしのコンストラクタ（テスト用など）
  public StudentCourse(String studentId, String courseName,
      LocalDateTime courseStartAt, LocalDateTime courseEndAt, String status) {
    this(null, studentId, courseName, courseStartAt, courseEndAt, status);
  }

  public class StudentSearchCondition {
    private String id;
    private String name;
    private String email;
    private String area;
    private Integer age;
    private String sex;

    public Object getId() {
      return getId();
    }
    public Object getName() {
      return getName();
  }
    public Object getEmail() {
      return getEmail();
    }

    public Object getArea() {
      return getArea();
    }
    public Object getAge() {
      return getAge();
    }

    public Object getSex() {
      return getSex();
    }
    }
  }