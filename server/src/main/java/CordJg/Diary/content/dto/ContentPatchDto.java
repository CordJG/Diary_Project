package CordJg.Diary.content.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@DynamicUpdate // 필드값을 다 안 채워도 채워진 것만 업데이트 되게 해준다
public class ContentPatchDto {
    private String title;
    private String body;
}
