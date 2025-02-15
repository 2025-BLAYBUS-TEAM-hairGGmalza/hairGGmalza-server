package hair.hairgg.designer.service;

import hair.hairgg.designer.domain.*;
import hair.hairgg.designer.repository.DesignerMajorRepository;
import hair.hairgg.designer.repository.DesignerRepository;
import hair.hairgg.designer.repository.MajorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class DesignerServiceImplTest {

    @Autowired
    private DesignerService designerService;

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private DesignerMajorRepository designerMajorRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Test
    void getDesignerById() {
        Major major1 = Major.builder()
                .name("펌")
                .build();
        Major savedMajor1 = majorRepository.save(major1);

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
                .major(savedMajor1)
                .build();

        newDesigner.addDesignerMajor(designerMajor);
        Designer savedDesigner = designerRepository.save(newDesigner);
        designerMajorRepository.save(designerMajor);

        Designer designer = designerService.getDesignerById(savedDesigner.getId());

        assertNotNull(designer);
        assertEquals(savedDesigner, designer);
        assertNotNull(designer.getDesignerMajors());
        assertEquals(savedMajor1, designer.getDesignerMajors().getFirst().getMajor());
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