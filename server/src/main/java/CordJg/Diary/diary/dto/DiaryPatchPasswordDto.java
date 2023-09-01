package CordJg.Diary.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DiaryPatchPasswordDto {

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;
}
