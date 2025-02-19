package hair.hairgg.favorite.converter;

import hair.hairgg.favorite.dto.FavoriteResponseDto.FavoriteResponse;

public class FavoriteConverter {

    public static FavoriteResponse toFavoriteResponse(Long designerId, Long favoriteId) {
        return FavoriteResponse.builder()
                .designerId(designerId)
                .isFavorite(favoriteId != null)
                .build();
    }
}
