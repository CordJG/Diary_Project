package CordJg.Diary.content.dto;

import CordJg.Diary.content.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ContentPatchDto {
    private String title;
    private String body;

    private LocalDate date;

}
