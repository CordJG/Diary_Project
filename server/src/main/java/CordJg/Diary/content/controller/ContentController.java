package CordJg.Diary.content.controller;

import CordJg.Diary.content.dto.ContentPostDto;
import CordJg.Diary.content.dto.ContentResponseDto;
import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.mapper.ContentMapper;
import CordJg.Diary.content.service.ContentService;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.repository.DiaryRepository;
import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
    public ResponseEntity postContent(@PathVariable("diary-id") @Positive long diaryId,
                                      @Valid @RequestBody ContentPostDto requestBody) {
        Content content = mapper.postToEntity(requestBody);

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DIARY_NOT_FOUND));

        content.setDiary(diary);

        Content createdContent = service.createContent(content);

        ContentResponseDto responseDto = mapper.entityToResponse(createdContent);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
