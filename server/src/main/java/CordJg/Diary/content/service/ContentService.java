package CordJg.Diary.content.service;

import CordJg.Diary.content.dto.ContentPatchDto;
import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.repository.ContentRepository;
import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private ContentRepository repository;


    public Content createContent(Content content) {

        return repository.save(content);
    }

    @Transactional
    public Content updateContent(ContentPatchDto content) {
        Content findedContent = findVerifiedContent(content.getDate());

        if(content.getModifyDate()!=null){
            verifyExistContent(content.getModifyDate());
            findedContent.setDate(content.getModifyDate());
        }
        if (content.getBody() != null) {
            findedContent.setBody(content.getBody());

        }
        if (content.getTitle() != null) {
            findedContent.setTitle(content.getTitle());
        }

        repository.save(findedContent);

        return findedContent;
    }

    public Content findContent(LocalDate date) {

        return repository.findByDate(date).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DATE_NOT_FOUND));
    }

    public Content findVerifiedContent(LocalDate date) {
        Optional<Content> optionalContent = repository.findByDate(date);

        Content findContent = optionalContent.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.DATE_NOT_FOUND));

        return findContent;
    }

    public void verifyExistContent(LocalDate modifiedDate) {
        Optional<Content> optionalContent = repository.findByDate(modifiedDate);
        if (optionalContent.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.DATE_EXISTS);
        }
    }

}
