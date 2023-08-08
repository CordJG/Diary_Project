package CordJg.Diary.content.mapper;

import CordJg.Diary.content.dto.ContentPatchDto;
import CordJg.Diary.content.dto.ContentPostDto;
import CordJg.Diary.content.dto.ContentResponseDto;
import CordJg.Diary.content.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ContentMapper {

    Content postToEntity(ContentPostDto postDto);

    Content patchToEntity(ContentPatchDto patchDto);

    ContentResponseDto EntityToResponse(Content content);
}
