package CordJg.Diary.security.auth.dto;

import lombok.Getter;

@Getter
public class LoginDto {
    private String email;
    private String password;
}