package hair.hairgg.designer.domain;

import hair.hairgg.designer.repository.DesignerRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MockDesignerService implements DesignerService {

	private final DesignerRepository designerRepository;
	@Override
	public Designer getDesignerById(Long id) {
		return designerRepository.findById(id).orElse(null);
	}


}
