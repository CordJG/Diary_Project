package CordJg.Diary.content.dto;

import CordJg.Diary.content.entity.Content;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
public class ContentPostDto {
    private String title;
    private String body;

    private LocalDate date;

    private Content.Feel feel;


    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }
}
