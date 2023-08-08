package CordJg.Diary.content.entity;

import CordJg.Diary.audit.Auditable;
import CordJg.Diary.diary.entity.Diary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Content extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DIARY_ID")
    private Diary diary;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Enumerated(value = EnumType.STRING)
    private Feel feel = Feel.NOT_BAD;

    @Column(unique = true)
    private LocalDate date;


    public enum Feel {

        GOOD,NOT_BAD, BAD;


    }



}
