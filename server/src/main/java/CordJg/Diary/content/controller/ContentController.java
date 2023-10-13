package CordJg.Diary.content.controller;

import CordJg.Diary.content.dto.ContentPatchDto;
import CordJg.Diary.content.dto.ContentPostDto;
import CordJg.Diary.content.dto.ContentResponseDto;
import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.mapper.ContentMapper;
import CordJg.Diary.content.service.ContentService;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.repository.DiaryRepository;
import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import CordJg.Diary.security.auth.loginResolver.LoginMemberId;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary/{diary-id}/content")
@Validated
@Slf4j
public class ContentController {

    private final ContentMapper mapper;
    private final ContentService service;
    private final DiaryRepository diaryRepository;

    @PostMapping
    @ApiOperation(value = "컨텐츠 생성")
    public ResponseEntity postContent(@PathVariable("diary-id") @Positive long diaryId,
                                      @Valid @RequestBody ContentPostDto requestBody,
                                      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Content content = mapper.postToEntity(requestBody);
        content.setDate(date);

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DIARY_NOT_FOUND));

        content.setDiary(diary);

        Content createdContent = service.createContent(content, date);

        ContentResponseDto responseDto = mapper.entityToResponse(createdContent);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity patchContent(@PathVariable("diary-id") @Positive long diaryId,
                                       @Valid @RequestBody ContentPatchDto requestBody,
                                       @LoginMemberId Long loginId,
                                       @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Content content = mapper.patchToEntity(requestBody);

        Content updatedContent = service.updateContent(diaryId, loginId,content, date);

        ContentResponseDto responseDto = mapper.entityToResponse(updatedContent);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/date")
    public ResponseEntity patchContentDate(@PathVariable("diary-id") @Positive long diaryId,
                                           @LoginMemberId Long loginId,
                                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                           @RequestParam("modifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate modifiedDate) {
        String message = service.updateContentDate(diaryId, loginId, date, modifiedDate);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping("/secret")
    public ResponseEntity updateContentSecret(@LoginMemberId Long loginId,
                                              @PathVariable("diary-id") @Positive long diaryId,
                                              @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        service.updateContentSecret(diaryId, loginId,date);

        return ResponseEntity.ok().body("비공개에서 공개로 변경하였습니다");
    }

    @GetMapping
    public ResponseEntity getContent(@PathVariable("diary-id") @Positive long diaryId,
                                     @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Content findContent = service.findContent(diaryId, date);

        ContentResponseDto responseDto = mapper.entityToResponse(findContent);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteContent(@PathVariable("diary-id") @Positive long diaryId,
                                        @LoginMemberId Long loginId,
                                        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        service.deleteContent(diaryId, loginId, date);

        return ResponseEntity.ok().body("성공적으로 삭제되었습니다");
    }

}
