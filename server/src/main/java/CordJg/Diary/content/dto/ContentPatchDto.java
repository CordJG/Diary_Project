package CordJg.Diary.content.dto;

import CordJg.Diary.content.entity.Content;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
public class ContentPatchDto {
    private String title;
    private String body;

    private Content.Feel feel;

    private LocalDate date;

    private LocalDate modifyDate;

}
