package CordJg.Diary.content.dto;

import javax.persistence.Column;
import java.time.LocalDate;

public class ContentPatchDto {
    private String title;
    private String body;

    @Column(unique = true)
    private LocalDate date;
    @Column(unique = true)
    private LocalDate modifyDate;

}
