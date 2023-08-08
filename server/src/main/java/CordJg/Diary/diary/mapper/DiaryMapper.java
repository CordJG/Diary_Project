package CordJg.Diary.diary.mapper;

import CordJg.Diary.diary.dto.DiaryPatchDto;
import CordJg.Diary.diary.dto.DiaryPostDto;
import CordJg.Diary.diary.dto.DiaryResponseDto;
import CordJg.Diary.diary.entity.Diary;
import CordJg.Diary.diary.repository.DiaryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DiaryMapper {

    Diary postToEntity(DiaryPostDto postDto);

    Diary patchToEntity(DiaryPatchDto patchDto);

    DiaryResponseDto EntityToResponseDto(Diary diary);
}
