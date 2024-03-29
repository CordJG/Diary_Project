package CordJg.Diary.content.dto;



import CordJg.Diary.diary.entity.Diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Setter
public class ContentResponseDto {

    private String title;
    private String body;
    private LocalDate date;
    private long diaryId;
    private String diaryName;
    private long memberId;
    private String memberName;
    private boolean secret;


    public void setDiary(Diary diary){
        this.memberId = diary.getMember().getMemberId();
        this.memberName = diary.getMember().getName();
        this.diaryId = diary.getDiaryId();
        this.diaryName = diary.getName();
    }
}
