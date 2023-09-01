package CordJg.Diary.diary.mapper;

import CordJg.Diary.diary.dto.DiaryPatchDto;
import CordJg.Diary.diary.dto.DiaryPostDto;
import CordJg.Diary.diary.dto.DiaryResponseDto;
import CordJg.Diary.diary.entity.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DiaryMapper {

    Diary postToEntity(DiaryPostDto postDto);

    Diary patchToEntity(DiaryPatchDto patchDto);

    DiaryResponseDto entityToResponseDto(Diary diary);

    List<DiaryResponseDto> entityListToResponseList(List<Diary> diarys);
}
