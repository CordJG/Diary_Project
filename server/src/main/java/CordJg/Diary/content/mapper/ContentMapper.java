package CordJg.Diary.content.mapper;

import CordJg.Diary.content.dto.ContentPatchDto;
import CordJg.Diary.content.dto.ContentPostDto;
import CordJg.Diary.content.dto.ContentResponseDto;
import CordJg.Diary.content.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ContentMapper {

    Content postToEntity(ContentPostDto postDto);

    Content patchToEntity(ContentPatchDto patchDto);

    ContentResponseDto entityToResponse(Content content);

    List<ContentResponseDto> entitysToResponses(List<Content> contents);
}
