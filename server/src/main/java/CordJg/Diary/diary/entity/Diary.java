package CordJg.Diary.diary.entity;

import CordJg.Diary.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DiaryId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String name;

}
