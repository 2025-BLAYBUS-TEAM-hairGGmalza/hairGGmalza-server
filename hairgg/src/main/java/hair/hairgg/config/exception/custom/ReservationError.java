package hair.hairgg.config.exception.custom;

import hair.hairgg.config.exception.ErrorCode;
import hair.hairgg.config.exception.GeneralException;

public class ReservationError extends GeneralException {
	public ReservationError(ErrorCode errorCode) {
		super(errorCode);
	}
}
