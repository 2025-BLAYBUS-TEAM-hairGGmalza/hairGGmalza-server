package hair.hairgg.designer.controller;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.designer.converter.DesignerConverter;
import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.dto.DesignerDto.DesignerInfos;
import hair.hairgg.designer.dto.SearchFilterDto;
import hair.hairgg.designer.service.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static hair.hairgg.designer.dto.DesignerDto.DesignerInfo;

@RestController
@RequestMapping("/designers")
@RequiredArgsConstructor
public class DesignerController {

    private final DesignerService designerService;

    @GetMapping("/{designerId}")
    public ApiResponse<DesignerInfo> getDesignerById(@PathVariable Long designerId) {
        Designer designer = designerService.getDesignerById(designerId);

        return ApiResponse.success("디자이너 조회 성공: " + designerId, DesignerConverter.toDesignerInfo(designer));
    }

    @PostMapping("")
    public ApiResponse<DesignerInfos> getAllDesigners(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestBody SearchFilterDto filter) {
        Page<Designer> designers = designerService.getDesignersWithFilter(page, filter);

        return ApiResponse.success("디자이너 목록 조회 성공", DesignerConverter.toDesignerInfoList(designers));
    }
}
