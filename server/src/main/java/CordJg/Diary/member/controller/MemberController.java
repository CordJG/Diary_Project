package CordJg.Diary.member.controller;

import CordJg.Diary.member.dto.MemberPatchDto;
import CordJg.Diary.member.dto.MemberPostDto;
import CordJg.Diary.member.dto.MemberResponseDto;
import CordJg.Diary.member.entity.Member;
import CordJg.Diary.member.mapper.MemberMapper;
import CordJg.Diary.member.service.MemberService;
import CordJg.Diary.response.MultiResponseDto;
import CordJg.Diary.response.SingleResponseDto;
import CordJg.Diary.security.auth.jwt.JwtTokenizer;
import CordJg.Diary.security.auth.loginResolver.LoginMemberId;
import CordJg.Diary.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberService memberService;
    private final MemberMapper mapper;

    private final JwtTokenizer jwtTokenizer;


    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto requestBody) {
        Member member = mapper.postToEntity(requestBody);

        Member createdMember = memberService.createMember(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createdMember.getMemberId());

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/info")
    public ResponseEntity getMemberInfo(@LoginMemberId Long memberId){
        Member findMember = memberService.findMember(memberId);
        MemberResponseDto response = mapper.entityToResponse(findMember);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }



    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String jws = authorizationHeader.substring(7);    // "Bearer " 이후의 토큰 문자열 추출

        jwtTokenizer.addToTokenBlackList(jws);     //블랙리스트에 jws 추가, 접근 막음

        return ResponseEntity.ok().body("Successfully logged out");
    }

//    @PostMapping("/image")
//    public ResponseEntity postImage(@LoginMemberId Long memberId,
//                                    @RequestParam("imageFile") MultipartFile imageFile) {
//        Member savedMember = memberService.uploadImage(memberId, imageFile);
//        MemberDto.ResponseDto response = mapper.memberToResponse(savedMember);
//
//        return new ResponseEntity<>((response),HttpStatus.OK);
//    }


    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){
        Member findMember = memberService.findMember(memberId);
        MemberResponseDto response = mapper.entityToResponse(findMember);
        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam(value = "page", defaultValue = "1") int page,
                                     @Positive @RequestParam(value = "size", defaultValue = "20") int size){
        Page<Member> pageMember = memberService.findMembers(page -1, size);
        List<Member> members = pageMember.getContent();
        List<MemberResponseDto> response = mapper.entitysToResponses(members);

        return new ResponseEntity<>(
                new MultiResponseDto<>(response, pageMember), HttpStatus.OK);
    }


    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @LoginMemberId Long loginId,
                                      @Valid @RequestBody MemberPatchDto requestBody){

        Member member = mapper.patchToEntity(requestBody);
        member.setMemberId(memberId);
        Member updatedMember = memberService.updateMember(loginId,member); //검증을 위한 메서드포함, loginId가 관리자가 아닌이상 memeberId와 다를 경우 권한없다는 오류 발생
        MemberResponseDto response = mapper.entityToResponse(updatedMember);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/status/active/{member-id}")     // Status를 Active로 변경
    public ResponseEntity patchStatusActive(@PathVariable("member-id") @Positive long memberId){
        Member activeMember =memberService.updateActiveStatus(memberId);

        MemberResponseDto response = mapper.entityToResponse(activeMember);

        return new ResponseEntity<>((response), HttpStatus.OK);
    }

    @PatchMapping("/status/delete/{member-id}")   // Status를 delete로 변경(로그인 못하게 막음)
    public ResponseEntity patchStatusDelete(@PathVariable("member-id") @Positive long memberId){

        Member deleteMember  = memberService.updateDeleteStatus(memberId);

        MemberResponseDto response = mapper.entityToResponse(deleteMember);

        return new ResponseEntity<>((response), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")    //Member 삭제
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId,
                                       @LoginMemberId Long loginId){


        memberService.deleteMember(loginId,memberId); //검증을 위한 메서드포함, loginId가 관리자가 아닌이상 memeberId와 다를 경우 권한없다는 오류 발생

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
