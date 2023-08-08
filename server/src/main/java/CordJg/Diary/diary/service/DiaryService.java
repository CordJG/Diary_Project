package CordJg.Diary.diary.service;

import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository repository;

    public Diary createDiary(Diary diary) {

        Diary createdDiary = repository.save(diary);

        return createdDiary;
    }
}
