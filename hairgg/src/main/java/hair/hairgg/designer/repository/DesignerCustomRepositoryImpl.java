package hair.hairgg.designer.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.designer.domain.QDesigner;
import hair.hairgg.designer.domain.Region;
import hair.hairgg.designer.dto.SearchFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DesignerCustomRepositoryImpl implements DesignerCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Designer> searchWithFilter(Pageable pageable, SearchFilterDto filter) {
        QDesigner designer = QDesigner.designer;
        BooleanBuilder builder = new BooleanBuilder();

        // MeetingType 조건 처리
        if (filter.getMeetingType() != null && filter.getMeetingType() != MeetingType.BOTH) {
            // MeetingType == ONLINE or OFFLINE. BOTH 모두 포함
            builder.and(
                    designer.meetingType.eq(filter.getMeetingType())
                            .or(designer.meetingType.eq(MeetingType.BOTH))
            );

            // MeetingType에 따른 가격 필터링
            if (filter.getMinPrice() != null || filter.getMaxPrice() != null) {
                NumberPath<Integer> priceField = (filter.getMeetingType() == MeetingType.ONLINE)
                        ? designer.onlinePrice
                        : designer.offlinePrice;

                addPriceFilter(builder, priceField, filter.getMinPrice(), filter.getMaxPrice());
            }
        } else {
            // MeetingType이 BOTH일 때, 온라인/오프라인 가격 모두 고려
            BooleanBuilder priceBuilder = new BooleanBuilder();

            if (filter.getMinPrice() != null || filter.getMaxPrice() != null) {
                BooleanBuilder onlinePriceCondition = new BooleanBuilder();
                BooleanBuilder offlinePriceCondition = new BooleanBuilder();

                // 디자이너의 meetingType이 ONLINE이거나 BOTH일 경우 onlinePrice 비교
                onlinePriceCondition.and(
                        designer.meetingType.eq(MeetingType.ONLINE)
                                .or(designer.meetingType.eq(MeetingType.BOTH))
                );
                addPriceFilter(onlinePriceCondition, designer.onlinePrice, filter.getMinPrice(), filter.getMaxPrice());

                // 디자이너의 meetingType이 OFFLINE이거나 BOTH일 경우 offlinePrice 비교
                offlinePriceCondition.and(
                        designer.meetingType.eq(MeetingType.OFFLINE)
                                .or(designer.meetingType.eq(MeetingType.BOTH))
                );
                addPriceFilter(offlinePriceCondition, designer.offlinePrice, filter.getMinPrice(), filter.getMaxPrice());

                // onlinePrice와 offlinePrice 비교를 OR로 결합
                priceBuilder.or(onlinePriceCondition);
                priceBuilder.or(offlinePriceCondition);

                builder.and(priceBuilder);
            }

        }

        // Region 조건 처리
        if (filter.getRegion() != null && filter.getRegion() != Region.서울전체) {
            builder.and(designer.region.eq(filter.getRegion()));
        }

        // 전체 개수 조회
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(designer.count())
                        .from(designer)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        // 페이징된 결과 조회
        List<Designer> designers = jpaQueryFactory
                .selectFrom(designer)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(designers, pageable, total);
    }

    private void addPriceFilter(BooleanBuilder builder, NumberPath<Integer> priceField, Integer minPrice, Integer maxPrice) {
        if (minPrice != null) {
            builder.and(priceField.goe(minPrice));
        }
        if (maxPrice != null) {
            builder.and(priceField.loe(maxPrice));
        }
    }
}
