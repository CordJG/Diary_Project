package CordJg.Diary.content.service;


import CordJg.Diary.content.dto.ContentPatchDto;
import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.repository.ContentRepository;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.repository.DiaryRepository;
import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import CordJg.Diary.member.entity.Member;
import CordJg.Diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
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
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;


    public Content createContent(Content content,LocalDate date) {

        verifyExistContent(content.getDiary().getDiaryId(),date);

        return repository.save(content);
    }


    public Content updateContent(long diaryId, long loginId, Content content, LocalDate date) {

        Diary findDiary = diaryRepository.findById(diaryId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DIARY_NOT_FOUND));

        verifyPermission(loginId,findDiary.getMember().getMemberId());

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

    public String updateContentDate(long diaryId, long loginId, LocalDate date, LocalDate modifiedDate) {
        Diary findDiary = diaryRepository.findById(diaryId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DIARY_NOT_FOUND));

        verifyPermission(loginId,findDiary.getMember().getMemberId());

        Content findedContent = findVerifiedContent(diaryId, date);

        verifyExistContent(diaryId, modifiedDate);

        findedContent.setDate(modifiedDate);

        String contentTitle = findedContent.getTitle();


        return contentTitle + "가 " + date + "에서 " + modifiedDate + "로 이동하였습니다";
    }

    public void updateContentSecret(long diaryId,long loginId, LocalDate date) {
        Diary findDiary = diaryRepository.findById(diaryId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DIARY_NOT_FOUND));

        verifyPermission(loginId,findDiary.getMember().getMemberId());

        Content findContent = findVerifiedContent(diaryId,date);

        findContent.setSecret(false);
    }

    public Content findContent(long diaryId, LocalDate date) {
        Content findedContent = findVerifiedContent(diaryId, date);

        return findedContent;
    }

    public void deleteContent(long diaryId, long loginId, LocalDate date) {
        Diary findDiary = diaryRepository.findById(diaryId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DIARY_NOT_FOUND));

        verifyPermission(loginId,findDiary.getMember().getMemberId());

        Content findContent = findVerifiedContent(diaryId, date);

        repository.delete(findContent);
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

    private void verifyPermission(Long loginId, long memeberId) {
        Member findMember = memberRepository.findById(loginId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if (!findMember.getRoles().contains("ADMIN")) {
            if (loginId != memeberId) {
                throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING);
            }
        }
    }

}
