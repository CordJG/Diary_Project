package CordJg.Diary.member.service;

import CordJg.Diary.exception.BusinessLogicException;
import CordJg.Diary.exception.ExceptionCode;
import CordJg.Diary.member.entity.Member;
import CordJg.Diary.member.repository.MemberRepository;
import CordJg.Diary.security.auth.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
//    private final FileStorageService fileStorageService;


    public Member createMember(Member member) {
        verifyExistEmail(member.getEmail());
        member.setName(verifyExistName(member.getName()));   //중복되는 이름 확인 후 중복되는 이름이 있을 시 뒤에 0~9999까지 번호를 붙여서 이름 저장

        // (3) 추가: Password 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        // (4) 추가: DB에 User Role 저장
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

//    public Member uploadImage(long memberId, MultipartFile imageFile) {
//        Member findMember = findVerifiedMember(memberId);
//        String fileUrl = fileStorageService.storeFile(imageFile);
//        findMember.setImage(fileUrl);
//
//        return memberRepository.save(findMember);
//    }

    public Member createMemberOAuth2(Member member) {

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        String newName = verifyExistName(member.getName());
        member.setName(newName);

        return memberRepository.save(member);
    }

    public Member findMember(long memberId) {
        return findVerifiedMemberById(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }


    public Member updateMember(Long loginId, Member member) {

        verifyPermission(loginId, member.getMemberId());

        Member findMember = findVerifiedMemberById(member.getMemberId());
        if(!member.getName().equals(findMember.getName())){                   //수정하려는 이름과 기존 이름이 다를 경우 수정하는 이름이 중복되는지 체크후 중복시 추가숫자를 덧붙여 이름수정
            findMember.setName(verifyExistName(member.getName()));
        }

        return findMember;
    }

    public Member updateActiveStatus(long memberId) {
        Member findMember = findVerifiedMemberById(memberId);
        findMember.setStatus(Member.Status.MEMBER_ACTIVE);

        return findMember;
    }

    public Member updateDeleteStatus(long memberId) {
        Member findMember = findVerifiedMemberById(memberId);

        findMember.setStatus(Member.Status.MEMBER_DELETE);
        return findMember;
    }

    public void deleteMember(Long loginId,long memberId ) {
        verifyPermission(loginId,memberId);

        Member findMember = findVerifiedMemberById(memberId);

        memberRepository.delete(findMember);
    }


    public Member findVerifiedMemberById(long memberId) {
        Optional<Member> optionalMember =  memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    public Member findVerifiedMemberByEmail(String email) {
        Optional<Member> optionalMember =  memberRepository.findByEmail(email);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));


        return findMember;
    }

    private void verifyExistEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public Boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private String verifyExistName(String name){     // oauth2로 로그인 했을 때 같은 이름이 있을 때 1~1000까지의 랜덤숫자를 붙임
        String newName = name;
        Optional<Member> optionalMember = memberRepository.findByName(name);
        if(optionalMember.isPresent()){
            Random random = new Random();
            int randomNumber = random.nextInt(10000) + 1;
            newName = name + randomNumber;
        }

        return newName;
    }
    public void verifyPermission(Long loginId, long memeberId) {
        Member findMember = findVerifiedMemberById(loginId);
        if (!findMember.getRoles().contains("ADMIN")) {
            if (loginId != memeberId) {
                throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING);
            }
        }
    }
}