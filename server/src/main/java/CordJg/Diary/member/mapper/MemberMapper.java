package CordJg.Diary.member.mapper;

import CordJg.Diary.member.dto.MemberPatchDto;
import CordJg.Diary.member.dto.MemberPostDto;
import CordJg.Diary.member.dto.MemberResponseDto;
import CordJg.Diary.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MemberMapper {

    Member postToEntity(MemberPostDto postDto);

    Member patchToEntity(MemberPatchDto patchDto);

    MemberResponseDto EntityToResponse(Member member);
}
