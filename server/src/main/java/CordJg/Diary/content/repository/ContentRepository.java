package CordJg.Diary.content.repository;

import CordJg.Diary.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Optional<Content> findByDiaryDiaryIdAndDate(Long diaryId, LocalDate date);

    Page<Content> findByDiaryDiaryId(Long diaryId, Pageable pageable);
}
