package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Schema(description = "受講生")
@Getter
@Setter
@AllArgsConstructor // すべてのフィールドを含むコンストラクタを自動生成
@NoArgsConstructor  // 引数なしのコンストラクタも必要な場合
@EqualsAndHashCode // equals と hashCode を自動生成
public class Student {

  @Pattern(regexp = "^\\d+$", message = "数字のみ入力するようにしてください。")
  private String id;

  @NotBlank
  private String name;

  @NotBlank
  private String kanaName;

  @NotBlank
  private String nickname;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String area;

  private int age;

  @NotBlank
  private String sex;

  private String remark;  // 備考
  private boolean isDeleted; // 削除フラグ
  private String telephone;  // 電話番号

  private List<Map<String, Object>> studentCourseList;  // 🔹 追加（コース情報）
}