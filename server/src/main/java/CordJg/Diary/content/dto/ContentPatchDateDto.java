package CordJg.Diary.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ContentPatchDateDto {
    private LocalDate date;

    private LocalDate modifyDate;
}
