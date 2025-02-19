package hair.hairgg.favorite.controller;

import hair.hairgg.apiPayLoad.ApiResponse;
import hair.hairgg.designer.converter.DesignerConverter;
import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.dto.DesignerDto.DesignerInfos;
import hair.hairgg.favorite.converter.FavoriteConverter;
import hair.hairgg.favorite.dto.FavoriteResponseDto.FavoriteResponse;
import hair.hairgg.favorite.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("")
    public ApiResponse<FavoriteResponse> postFavorite(
            HttpServletRequest request,
            @RequestParam("designerId") Long designerId
    ) {
        Long memberId = (Long) request.getAttribute("id");
        Long favoriteId = favoriteService.checkFavoriteDesigner(memberId, designerId);

        return ApiResponse.success("디자이너 찜 결과 반환", FavoriteConverter.toFavoriteResponse(designerId, favoriteId));
    }

    @GetMapping("")
    public ApiResponse<DesignerInfos> getFavorite(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Long memberId = (Long) request.getAttribute("id");
        Page<Designer> designers = favoriteService.getFavoriteDesigners(memberId, page);

        return ApiResponse.success("찜한 디자이너 조회 성공", DesignerConverter.toDesignerInfoList(designers));
    }
}
