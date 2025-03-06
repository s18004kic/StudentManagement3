package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@AllArgsConstructor
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

  // デフォルトコンストラクタ（必要なら追加）
  public StudentCourse() {}

  // IDなしのコンストラクタ（テスト用など）
  public StudentCourse(String studentId, String courseName,
      LocalDateTime courseStartAt, LocalDateTime courseEndAt, String status) {
    this(null, studentId, courseName, courseStartAt, courseEndAt, status);
  }

  @Override
  public String toString() {
    return "StudentCourse{" +
        "Id='" + id + '\'' +
        ", studentId='" + studentId + '\'' +
        ", courseName='" + courseName + '\'' +
        ", courseStartAt=" + courseStartAt +
        ", courseEndAt=" + courseEndAt +
        ", status='" + status + '\'' +
        '}';
  }
}