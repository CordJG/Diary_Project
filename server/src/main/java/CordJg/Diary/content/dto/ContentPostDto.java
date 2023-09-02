package CordJg.Diary.content.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;



@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ContentPostDto {

    @NotBlank
    private String title;
    @NotBlank
    private String body;

}
