package CordJg.Diary.diary.service;

import CordJg.Diary.content.entity.Content;
import CordJg.Diary.content.repository.ContentRepository;
import CordJg.Diary.diary.dto.DiaryPatchDto;
import CordJg.Diary.diary.dto.DiaryPatchPasswordDto;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.repository.DiaryRepository;
import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import CordJg.Diary.member.entity.Member;
import CordJg.Diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private final MemberRepository memberRepository;

    private final ContentRepository contentRepository;

    public Diary createDiary(Diary diary) {

        Diary createdDiary = diaryRepository.save(diary);

        return createdDiary;
    }

    public Diary updateDiary(Long loginId, DiaryPatchDto patchDto, long diaryId) {



        Diary findDiary = findVerifiedDiaryById(diaryId);

        verifyPermission(loginId, findDiary.getMember().getMemberId());

        checkPassword(patchDto.getPassword(), findDiary);


        if (patchDto.getName() !=null) {
            findDiary.setName(patchDto.getName());
        }
        if (patchDto.getPassword() !=null) {
            findDiary.setPassword(patchDto.getPassword());
        }

        return findDiary;
    }

    public void updatePassword(Long loginId, DiaryPatchPasswordDto patchDto, long diaryId) {
        Diary findDiary = findVerifiedDiaryById(diaryId);

        verifyPermission(loginId, findDiary.getMember().getMemberId());

        checkPassword(patchDto.getPassword(), findDiary);

        findDiary.setPassword(patchDto.getNewPassword());
    }

    public Diary findDiary(long diaryId) {
        Diary findDiary = findVerifiedDiaryById(diaryId);

        return findDiary;
    }
    public List<Diary> findDiarys(long loginId) {

        Member loginMember = memberRepository.findById(loginId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        List<Diary> diaries = loginMember.getDiarys();

        return diaries;
    }

    public Page<Content> findContents(long diaryId, int page, int size) {
        return contentRepository.findByDiaryDiaryId(diaryId,PageRequest.of(page, size, Sort.by("contentId").descending()));
    }

    public String deleteDiary(long loginId,  DiaryPatchDto patchDto,  long diaryId) {

        Diary findDiary = findVerifiedDiaryById(diaryId);

        verifyPermission(loginId, findDiary.getMember().getMemberId());

        checkPassword(patchDto.getPassword(),findDiary);

        diaryRepository.deleteById(diaryId);

        return findDiary.getName();
    }


    private Diary findVerifiedDiaryById(long diaryId) {
        Diary findDiary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DIARY_NOT_FOUND));

        return findDiary;
    }



    public void verifyPermission(Long loginId, long memeberId) {
        Member findMember = memberRepository.findById(loginId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if (!findMember.getRoles().contains("ADMIN")) {
            if (loginId != memeberId) {
                throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING);
            }
        }
    }

    public void checkPassword(String password, Diary findDiary){
        if(findDiary.getPassword()!=null) {
            if (!password.equals(findDiary.getPassword())) {
                throw new BusinessLogicException(ExceptionCode.PASSWORD_IS_NOT_CORRECT);
            }
        }
    }
}
