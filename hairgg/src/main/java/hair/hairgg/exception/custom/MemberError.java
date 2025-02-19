package hair.hairgg.exception.custom;

import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.GeneralException;

public class MemberError extends GeneralException {
	public MemberError(ErrorCode errorCode) {
		super(errorCode);
	}
}
