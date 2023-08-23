package CordJg.Diary.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MemberPostDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;

    @NotBlank
    private String password;


}
