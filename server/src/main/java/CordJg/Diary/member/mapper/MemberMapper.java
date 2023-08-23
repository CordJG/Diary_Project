package CordJg.Diary.member.mapper;

import CordJg.Diary.member.dto.MemberPatchDto;
import CordJg.Diary.member.dto.MemberPostDto;
import CordJg.Diary.member.dto.MemberResponseDto;
import CordJg.Diary.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MemberMapper {

    Member postToEntity(MemberPostDto postDto);

    Member patchToEntity(MemberPatchDto patchDto);

    MemberResponseDto entityToResponse(Member member);
    List<MemberResponseDto> entitysToResponses(List<Member> members);
}
