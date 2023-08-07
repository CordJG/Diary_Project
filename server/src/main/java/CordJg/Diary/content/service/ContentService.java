package CordJg.Diary.content.service;

import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private ContentRepository repository;


    public Content createContent(Content content) {

        return repository.save(content);
    }

    public Content updateContent(Content content) {
        Content findedContent = findVerifiedContent(content.getDate());

    }

    public Content findVerifiedContent(LocalDate date) {
        Optional<Content> optionalContent = repository.findByDate(date);
        return optionalContent.orElseThrow();
    }

}
