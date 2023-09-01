package CordJg.Diary.content.dto;

import CordJg.Diary.content.entity.Content;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.member.entity.Member;
import lombok.AllArgsConstructor;
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
    private String createdAt;
    private String modifiedAt;
    private long diaryId;
    private String diaryName;
    private long memberId;
    private String memberName;

    public void setDiary(Diary diary){
        this.memberId = diary.getMember().getMemberId();
        this.memberName = diary.getMember().getName();
        this.diaryId = diary.getDiaryId();
        this.diaryName = diary.getName();
    }
}
