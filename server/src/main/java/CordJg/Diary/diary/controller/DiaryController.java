package CordJg.Diary.diary.controller;

import CordJg.Diary.diary.dto.DiaryPostDto;
import CordJg.Diary.diary.dto.DiaryResponseDto;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.mapper.DiaryMapper;
import CordJg.Diary.diary.service.DiaryService;
import CordJg.Diary.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/diary")
@Valid
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryMapper mapper;
    private final DiaryService service;


    @PostMapping
    public ResponseEntity createDiary(@Valid @RequestBody DiaryPostDto requestBoudy) {
        Diary diary = mapper.postToEntity(requestBoudy);
        Member newMember = new Member();
        newMember.setMemberId(1l);
        newMember.setName("이재관");
        newMember.setEmail("wjrmffldglem@gmail.com");

        diary.setMember(newMember);

        Diary createdDiary = service.createDiary(diary);
        DiaryResponseDto responseDto = mapper.EntityToResponseDto(createdDiary);

        responseDto.setMemberId(newMember.getMemberId());
        responseDto.setMemberName(newMember.getName());


        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
