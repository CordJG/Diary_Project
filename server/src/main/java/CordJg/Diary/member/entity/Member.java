package CordJg.Diary.member.entity;

import javax.persistence.*;
import java.util.*;

import CordJg.Diary.audit.Auditable;
import lombok.*;


@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //MySql 사용 할 거기 때문에 IDENTITY 전략사용
    private Long memberId;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Column
    private String image="";

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.MEMBER_ACTIVE;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public enum Status {
        MEMBER_ACTIVE("활동중"),
        MEMBER_DELETE("탈퇴 상태");

        private String status;

        Status(String status) {
            this.status = status;}
    }
}
