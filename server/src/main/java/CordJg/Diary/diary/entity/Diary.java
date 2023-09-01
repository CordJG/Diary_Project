package CordJg.Diary.diary.entity;

import CordJg.Diary.audit.Auditable;
import CordJg.Diary.content.entity.Content;
import CordJg.Diary.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Diary extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DiaryId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100)
    private String password;

    @OneToMany(mappedBy = "diary", cascade = {CascadeType.ALL})
    private List<Content> contents = new ArrayList<>();

}
