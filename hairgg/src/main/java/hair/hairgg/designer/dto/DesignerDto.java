package hair.hairgg.designer.dto;

import hair.hairgg.designer.domain.Major;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.designer.domain.Region;
import lombok.Builder;

import java.util.List;

public class DesignerDto {

    @Builder
    public record DesignerInfo(
            Long designerId,
            String name,
            Region region,
            String address,
            String profile,
            String portfolio1,
            String portfolio2,
            String description,
            int offlinePrice,
            int onlinePrice,
            MeetingType meetingType,
            List<Major> majors
    ) {
    }

    @Builder
    public record DesignerInfos(
            List<DesignerInfo> designerInfos,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {
    }

    @Builder
    public record DesignerInfobrief(
            Long designerId,
            String name,
            Region region,
            String address,
            String profile,
            String description
    ) {
    }
}
