package hair.hairgg.exception.custom;

import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.GeneralException;

public class ReservationError extends GeneralException {
	public ReservationError(ErrorCode errorCode) {
		super(errorCode);
	}
}
