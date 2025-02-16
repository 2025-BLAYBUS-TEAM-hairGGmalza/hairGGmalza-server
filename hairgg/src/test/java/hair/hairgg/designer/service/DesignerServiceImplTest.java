package hair.hairgg.designer.service;

import hair.hairgg.designer.domain.*;
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
    MyMockMajorRepository majorRepository;
    MyMockDesignerMajorRepository designerMajorRepository;
    DesignerService designerService;

    private static class MyMockDesignerRepository extends MockDesignerRepository {

        private final Map<Long, Designer> database = new HashMap<>();
        private long sequence = 1L;

        @Override
        public <S extends Designer> S save(S entity) {
            if (entity.getId() == null) {
                setPrivateField(entity, "id", sequence++);
            }
            database.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public Optional<Designer> findById(Long id) {
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

    private static class MyMockMajorRepository extends MockMajorRepository {

        private final Map<Long, Major> database = new HashMap<>();
        private long sequence = 1L;

        @Override
        public <S extends Major> S save(S entity) {
            if (entity.getId() == null) {
                setPrivateField(entity, "id", sequence++);
            }
            database.put(entity.getId(), entity);
            return entity;
        }
    }

    private static class MyMockDesignerMajorRepository extends MockDesignerMajorRepository {

        private final Map<Long, DesignerMajor> database = new HashMap<>();
        private long sequence = 1L;

        @Override
        public <S extends DesignerMajor> S save(S entity) {
            if (entity.getId() == null) {
                setPrivateField(entity, "id", sequence++);
            }
            database.put(entity.getId(), entity);
            return entity;
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
        majorRepository = new MyMockMajorRepository();
        designerMajorRepository = new MyMockDesignerMajorRepository();
        designerService = new DesignerServiceImpl(designerRepository, designerMajorRepository);
    }

    @Test
    void Id로_디자이너_조회() {
        Major major1 = Major.builder()
                .name("펌")
                .build();

        Designer newDesigner = Designer.builder()
                .name("사용자")
                .region(Region.서울전체)
                .address("블레이버스 옥탑방")
                .profile("example@example.com")
                .description("해커톤 재밌네")
                .offlinePrice(40000)
                .onlinePrice(30000)
                .meetingType(MeetingType.BOTH)
                .build();

        DesignerMajor designerMajor = DesignerMajor.builder()
                .major(major1)
                .build();

        newDesigner.addDesignerMajor(designerMajor);
        Designer savedDesigner = designerRepository.save(newDesigner);
        designerMajorRepository.save(designerMajor);

        Designer designer = designerService.getDesignerById(savedDesigner.getId());

        assertNotNull(designer);
        assertEquals(savedDesigner, designer);
        assertNotNull(designer.getDesignerMajors());
        assertEquals(major1, designer.getDesignerMajors().getFirst().getMajor());
    }

    @Test
    void getDesigners() {
        Designer newDesigner1 = Designer.builder()
                .name("사용자1")
                .region(Region.서울전체)
                .address("블레이버스 옥탑방")
                .profile("example1@example.com")
                .description("해커톤 재밌네")
                .offlinePrice(40000)
                .onlinePrice(30000)
                .meetingType(MeetingType.BOTH)
                .build();
        designerRepository.save(newDesigner1);

        Designer newDesigner2 = Designer.builder()
                .name("사용자2")
                .region(Region.홍대_연남_합정)
                .address("홍대 빈티지")
                .profile("example2@example.com")
                .description("테스트 코드 재밌네")
                .offlinePrice(45000)
                .onlinePrice(35000)
                .meetingType(MeetingType.ONLINE)
                .build();
        designerRepository.save(newDesigner2);

        Page<Designer> designers = designerService.getDesigners(0);

        assertNotNull(designers);
        assertEquals(2, designers.getTotalElements());
        assertEquals("사용자1", designers.getContent().get(0).getName());
    }
}