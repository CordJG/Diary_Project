package CordJg.Diary.diary.entity;

import CordJg.Diary.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String name;

}
