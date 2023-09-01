package CordJg.Diary.diary.service;

import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.repository.DiaryRepository;
import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import CordJg.Diary.member.entity.Member;
import CordJg.Diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private final MemberRepository memberRepository;

    public Diary createDiary(Diary diary) {

        Diary createdDiary = diaryRepository.save(diary);

        return createdDiary;
    }

    public Diary updateDiary(Long loginId, Diary diary) {

        verifyPermission(loginId, diary.getMember().getMemberId());

        Diary findDiary = findVerifiedDiaryById(diary.getDiaryId());

        if (!diary.getName().isEmpty()) {
            findDiary.setName(diary.getName());
        }
        if (!diary.getPassword().isEmpty()) {
            findDiary.setPassword(diary.getPassword());
        }

        return findDiary;
    }

    public List<Diary> findDiary(Long loginId) {

        Member loginMember = memberRepository.findById(loginId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        List<Diary> diaries = loginMember.getDiarys();

        return diaries;
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


}
