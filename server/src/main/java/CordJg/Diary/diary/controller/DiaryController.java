package CordJg.Diary.diary.controller;

import CordJg.Diary.content.dto.ContentResponseDto;
import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.mapper.ContentMapper;
import CordJg.Diary.diary.dto.DiaryPatchDto;
import CordJg.Diary.diary.dto.DiaryPatchPasswordDto;
import CordJg.Diary.diary.dto.DiaryPostDto;
import CordJg.Diary.diary.dto.DiaryResponseDto;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.mapper.DiaryMapper;
import CordJg.Diary.diary.service.DiaryService;

import CordJg.Diary.member.repository.MemberRepository;
import CordJg.Diary.response.MultiResponseDto;
import CordJg.Diary.response.SingleResponseDto;
import CordJg.Diary.security.auth.loginResolver.LoginMemberId;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/diary")
@Valid
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryMapper mapper;
    private final DiaryService service;
    private final MemberRepository memberRepository;
    private final ContentMapper contentMapper;


    @PostMapping
    public ResponseEntity createDiary(@LoginMemberId Long memberId,
                                      @Valid @RequestBody DiaryPostDto requestBoudy) {
        Diary diary = mapper.postToEntity(requestBoudy);

        diary.setMember(memberRepository.getReferenceById(memberId));

        Diary createdDiary = service.createDiary(diary);
        DiaryResponseDto responseDto = mapper.entityToResponseDto(createdDiary);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getDiaryList(@LoginMemberId Long memberId) {
        List<Diary> findDiarys = service.findDiarys(memberId);
        List<DiaryResponseDto> responses = mapper.entityListToResponseList(findDiarys);

        return ResponseEntity.ok(new SingleResponseDto<>(responses));
    }

    @GetMapping("/{diary-id}/content")
    public ResponseEntity getContents(@Positive @RequestParam(value = "page", defaultValue = "1") int page,
                                      @Positive @RequestParam(value = "size", defaultValue = "10") int size,
                                      @PathVariable("diary-id") @Positive long diaryId) {
        Page<Content> pageContent = service.findContents(diaryId, page-1, size);
        List<Content> contents = pageContent.getContent();
        List<ContentResponseDto> response = contentMapper.entitysToResponses(contents);

        return ResponseEntity.ok(new MultiResponseDto<>(response, pageContent));
    }

    @PatchMapping("/{diary-id}")
    public ResponseEntity updateDiary(@LoginMemberId Long memberId,
                                      @PathVariable("diary-id") @Positive long diaryId,
                                      @Valid @RequestBody DiaryPatchDto requestBody) {
        Diary updatedDiary = service.updateDiary(memberId, requestBody, diaryId);
        DiaryResponseDto response = mapper.entityToResponseDto(updatedDiary);

        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @PatchMapping("/{diary-id}/password")
    public ResponseEntity updateDiaryPassword(@LoginMemberId Long memberId,
                                              @PathVariable("diary-id") @Positive long diaryId,
                                              @Valid @RequestBody DiaryPatchPasswordDto requestBody) {
        service.updatePassword(memberId, requestBody, diaryId);

        return ResponseEntity.ok().body("비밀번호가 변경되었습니다");
    }

    @DeleteMapping("/{diary-id}")
    public ResponseEntity deleteDiary(@LoginMemberId Long memberId,
                                      @PathVariable("diary-id") @Positive long diaryId,
                                      @Valid @RequestBody DiaryPatchDto requestBody) {

        String diaryName = service.deleteDiary(memberId, requestBody, diaryId);

        return ResponseEntity.ok().body(diaryName + " 다이어리가 삭제되었습니다");
    }
}
