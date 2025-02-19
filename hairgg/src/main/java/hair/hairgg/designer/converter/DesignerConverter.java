package hair.hairgg.designer.converter;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.domain.DesignerMajor;
import hair.hairgg.designer.domain.Major;
import hair.hairgg.designer.dto.DesignerDto;
import hair.hairgg.designer.dto.DesignerDto.DesignerInfo;
import hair.hairgg.designer.dto.DesignerDto.DesignerInfos;
import org.springframework.data.domain.Page;

import java.util.List;

public class DesignerConverter {

    public static DesignerInfo toDesignerInfo(Designer designer) {
        List<String> majors = designer.getDesignerMajors().stream().map(
                designerMajor -> designerMajor.getMajor().getName()
        ).toList();

        return DesignerInfo.builder()
                .designerId(designer.getId())
                .name(designer.getName())
                .region(designer.getRegion())
                .address(designer.getAddress())
                .profile(designer.getProfile())
                .portfolio1(designer.getPortfolio1())
                .portfolio2(designer.getPortfolio2())
                .description(designer.getDescription())
                .offlinePrice(designer.getOfflinePrice())
                .onlinePrice(designer.getOnlinePrice())
                .meetingType(designer.getMeetingType())
                .majors(majors)
                .favoriteCount(designer.getFavoriteCount())
                .build();
    }

    public static DesignerInfos toDesignerInfoList(Page<Designer> designers) {
        List<DesignerInfo> designerInfoList = designers.stream()
                .map(DesignerConverter::toDesignerInfo)
                .toList();

        return DesignerInfos.builder()
                .designerInfos(designerInfoList)
                .listSize(designerInfoList.size())
                .totalPage(designers.getTotalPages())
                .totalElements(designers.getTotalElements())
                .isFirst(designers.isFirst())
                .isLast(designers.isLast())
                .build();
    }

    public static DesignerDto.DesignerInfobrief toDesignerInfobrief(Designer designer) {
        return DesignerDto.DesignerInfobrief.builder()
            .designerId(designer.getId())
            .name(designer.getName())
            .region(designer.getRegion())
            .address(designer.getAddress())
            .profile(designer.getProfile())
            .description(designer.getDescription())
            .build();
    }
}
