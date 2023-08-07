package CordJg.Diary.content.entity;

import CordJg.Diary.diary.entity.Diary;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Diary_id")
    private Diary diary;

    private String title;
    private String body;
    private LocalDate date;


}
