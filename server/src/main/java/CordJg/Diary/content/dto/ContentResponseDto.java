package CordJg.Diary.content.dto;

import CordJg.Diary.content.entity.Content;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ContentResponseDto {

    private String title;
    private String body;
    private Content.Feel feel;
    private LocalDate date;
    private String createdAt;
    private String modifiedAt;
    private long diaryId;
    private String diaryName;
    private long memberId;
    private String memberName;
}
