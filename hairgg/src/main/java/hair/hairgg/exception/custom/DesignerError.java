package hair.hairgg.exception.custom;

import hair.hairgg.exception.ErrorCode;
import hair.hairgg.exception.GeneralException;

public class DesignerError extends GeneralException {
    public DesignerError(ErrorCode errorCode) {
        super(errorCode);
    }
}
