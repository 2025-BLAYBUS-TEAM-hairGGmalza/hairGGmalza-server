package hair.hairgg.designer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.designer.domain.Region;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchFilterDto {
    MeetingType meetingType;
    Region region;
    Integer minPrice;
    Integer maxPrice;
    List<String> majors;
}
