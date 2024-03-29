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
public class Content  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;

    @ManyToOne
    @JoinColumn(name = "DIARY_ID")
    private Diary diary;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private boolean secret=true;

    @Column(unique = true)
    private LocalDate date;

}
