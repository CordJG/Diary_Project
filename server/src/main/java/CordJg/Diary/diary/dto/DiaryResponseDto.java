package CordJg.Diary.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DiaryResponseDto {
    private long diaryId;
    private String name;
    private long memberId;
    private String memberName;
}
