package hair.hairgg.mock.designer;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.domain.DesignerService;
import hair.hairgg.designer.domain.MeetingType;
import hair.hairgg.designer.domain.Region;
import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.GeneralException;

public class MockDesignerService implements DesignerService {
	@Override
	public Designer getDesignerById(Long id) {
		if (id == 1) {
			return new Designer(1L, "김디자이너", Region.SEOUL, "서울시 강남구", "fdsd", "fdsf", 1000, 2000, MeetingType.BOTH);
		}
		throw new GeneralException(ErrorCode.DESIGNER_NOT_FOUND);
	}
}
