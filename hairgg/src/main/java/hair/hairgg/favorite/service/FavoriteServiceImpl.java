package hair.hairgg.favorite.service;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.service.DesignerService;
import hair.hairgg.favorite.domain.Favorite;
import hair.hairgg.favorite.repository.FavoriteRepository;
import hair.hairgg.member.Member;
import hair.hairgg.member.MemberRepository;
import hair.hairgg.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final DesignerService designerService;

    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;


    @Override
    @Transactional
    public Long checkFavoriteDesigner(Long memberId, Long designerId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("No member")
        );

        Designer designer = designerService.getDesignerById(designerId);
        Favorite favorite = favoriteRepository.findFavoriteByMemberIdAndDesignerId(memberId, designerId);

        if (favorite == null) {
            favorite = Favorite.builder()
                    .member(member)
                    .designer(designer)
                    .build();
            designer.addFavorite();
            return favoriteRepository.save(favorite).getId();
        }

        favoriteRepository.delete(favorite);
        designer.deleteFavorite();
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Designer> getFavoriteDesigners(Long memberId, Integer page) {
        Pageable pageable = PageRequest.of(page, 30, Sort.by("id").ascending());
        List<Designer> designers = favoriteRepository.findDesignerByMemberId(memberId);
        long count = favoriteRepository.countByMemberId(memberId);

        return new PageImpl<>(designers, pageable, count);
    }
}
