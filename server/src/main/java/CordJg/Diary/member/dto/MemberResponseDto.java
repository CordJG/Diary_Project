package CordJg.Diary.member.dto;

import CordJg.Diary.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private long memberId;
    private String name;
    private String email;
    private String image;
    private Member.Status status;

    private String createdAt;
    private String modifiedAt;
}
