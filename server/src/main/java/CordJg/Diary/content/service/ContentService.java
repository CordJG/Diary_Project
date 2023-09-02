package CordJg.Diary.content.service;


import CordJg.Diary.content.dto.ContentPatchDto;
import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.repository.ContentRepository;
import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentService {

    private final ContentRepository repository;


    public Content createContent(Content content,LocalDate date) {

        verifyExistContent(content.getDiary().getDiaryId(),date);

        return repository.save(content);
    }


    public Content updateContent(long diaryId, ContentPatchDto content, LocalDate date) {
        Content findedContent = findVerifiedContent(diaryId, date);


        if (content.getBody() != null) {
            findedContent.setBody(content.getBody());
        }
        if (content.getTitle() != null) {
            findedContent.setTitle(content.getTitle());
        }

        repository.save(findedContent);

        return findedContent;
    }

    public String updateContentDate(long diaryId, LocalDate date, LocalDate modifiedDate) {
        Content findedContent = findVerifiedContent(diaryId, date);

        verifyExistContent(diaryId, modifiedDate);

        findedContent.setDate(modifiedDate);

        String contentTitle = findedContent.getTitle();


        return contentTitle + "가 " + date + "에서 " + modifiedDate + "로 이동하였습니다";
    }


    public Content findVerifiedContent(long diaryId, LocalDate date) {
        Optional<Content> optionalContent = repository.findByDiaryDiaryIdAndDate(diaryId, date);

        Content findContent = optionalContent.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.DATE_NOT_FOUND));

        return findContent;
    }

    public void verifyExistContent(long diaryId, LocalDate modifiedDate) {
        Optional<Content> optionalContent = repository.findByDiaryDiaryIdAndDate(diaryId, modifiedDate);
        if (optionalContent.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.DATE_EXISTS);
        }
    }

}
