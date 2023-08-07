package CordJg.Diary.content.dto;

import javax.persistence.Column;
import java.time.LocalDate;

public class ContentPostDto {
    private String title;
    private String body;

    @Column(unique = true)
    private LocalDate date;


    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }
}
