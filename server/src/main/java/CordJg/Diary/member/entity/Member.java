package CordJg.Diary.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;
import lombok.*;


@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //MySql 사용 할 거기 때문에 IDENTITY 전략사용
    private Long memberId;

    private String email;
    private String image;
    private int password;
}
