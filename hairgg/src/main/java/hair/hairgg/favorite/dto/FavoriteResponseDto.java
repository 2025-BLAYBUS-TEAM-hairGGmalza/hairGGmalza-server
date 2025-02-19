package hair.hairgg.favorite.dto;

import lombok.Builder;

public class FavoriteResponseDto {

    @Builder
    public record FavoriteResponse(
            Long designerId,
            Boolean isFavorite
    ) {
    }
}
