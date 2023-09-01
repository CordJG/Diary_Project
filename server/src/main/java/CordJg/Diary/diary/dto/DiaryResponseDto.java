package CordJg.Diary.diary.dto;

import CordJg.Diary.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class DiaryResponseDto {
    private long diaryId;
    private String name;
    private long memberId;
    private String memberName;
    private String createdAt;
    private String modifiedAt;

    public void setMember(Member member){
        this.memberId = member.getMemberId();
        this.memberName = member.getName();
    }
}
