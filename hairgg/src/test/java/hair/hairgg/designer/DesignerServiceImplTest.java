package hair.hairgg.designer;

import hair.hairgg.designer.domain.*;
import hair.hairgg.designer.service.DesignerService;
import hair.hairgg.designer.service.DesignerServiceImpl;
import hair.hairgg.mock.designer.MockDesignerRepository;
import hair.hairgg.mock.designerMajor.MockDesignerMajorRepository;
import hair.hairgg.mock.major.MockMajorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DesignerServiceImplTest {

    MyMockDesignerRepository designerRepository;
    DesignerService designerService;

    private static class MyMockDesignerRepository extends MockDesignerRepository {

        private final Map<Long, Designer> database = new HashMap<>();
        private Long sequence = 1L;

        @Override
        public <S extends Designer> S save(S entity) {
            if (entity.getId() == null) {
                setPrivateField(entity, "id", sequence++);
            }
            database.put(entity.getId(), entity);
            System.out.println("save: " + entity.getId());
            return entity;
        }

        @Override
        public Optional<Designer> findByDesignerId(Long id) {
            System.out.println("find: " + id);
            return Optional.ofNullable(database.get(id));
        }

        @Override
        public Page<Designer> findAll(Pageable pageable) {
            List<Designer> allDesigners = new ArrayList<>(database.values());

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), allDesigners.size());

            List<Designer> pagedList = allDesigners.stream()
                    .skip(start)
                    .limit(pageable.getPageSize())
                    .collect(Collectors.toList());

            return new PageImpl<>(pagedList, pageable, allDesigners.size());
        }
    }

    private static void setPrivateField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("ID 자동 할당 실패", e);
        }
    }

    @BeforeEach
    void setUp() {
        designerRepository = new MyMockDesignerRepository();
        designerService = new DesignerServiceImpl(designerRepository);
    }

    @Test
    void Id로_디자이너_조회() {
        // given
        Designer designer = Designer.builder()
                .id(1L)
                .name("사용자")
                .region(Region.서울전체)
                .address("서울시 중앙대학교")
                .profile("imageUrl")
                .description("테스트 코드 작성 재밌네")
                .offlinePrice(40000)
                .onlinePrice(30000)
                .meetingType(MeetingType.BOTH)
                .portfolio1("example portfolio1")
                .portfolio2("example portfolio2")
                .favoriteCount(0)
                .designerMajors(Collections.emptyList())
                .build();
        designerRepository.save(designer);

        //when
        Designer result = designerService.getDesignerById(1L);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("사용자", result.getName());
        assertEquals(Region.서울전체, result.getRegion());
        assertEquals("서울시 중앙대학교", result.getAddress());
        assertEquals("imageUrl", result.getProfile());
        assertEquals("테스트 코드 작성 재밌네", result.getDescription());
        assertEquals(40000, result.getOfflinePrice());
        assertEquals(30000, result.getOnlinePrice());
        assertEquals(MeetingType.BOTH, result.getMeetingType());
        assertEquals("example portfolio1", result.getPortfolio1());
        assertEquals("example portfolio2", result.getPortfolio2());
        assertEquals(0, result.getFavoriteCount());
    }

    @Test
    void getDesigners() {
        // given
        Designer newDesigner1 = Designer.builder()
                .id(1L)
                .name("사용자1")
                .region(Region.서울전체)
                .address("중앙대학교")
                .profile("imageUrl1")
                .description("테스트 코드 작성")
                .offlinePrice(40000)
                .onlinePrice(30000)
                .meetingType(MeetingType.BOTH)
                .portfolio1("example portfolio1-1")
                .portfolio2("example portfolio1-2")
                .favoriteCount(0)
                .designerMajors(Collections.emptyList())
                .build();
        designerRepository.save(newDesigner1);

        Designer newDesigner2 = Designer.builder()
                .id(2L)
                .name("사용자2")
                .region(Region.홍대_연남_합정)
                .address("숭실대학교")
                .profile("imageUrl2")
                .description("테스트 코드 재밌네")
                .offlinePrice(45000)
                .onlinePrice(35000)
                .meetingType(MeetingType.ONLINE)
                .portfolio1("example portfolio2-1")
                .portfolio2("example portfolio2-2")
                .favoriteCount(0)
                .designerMajors(Collections.emptyList())
                .build();
        designerRepository.save(newDesigner2);

        // when
        Page<Designer> designers = designerService.getDesigners(0);

        // then
        assertNotNull(designers);
        assertEquals(2, designers.getTotalElements());
        assertEquals("사용자1", designers.getContent().get(0).getName());
        assertEquals("사용자2", designers.getContent().get(1).getName());
    }
}