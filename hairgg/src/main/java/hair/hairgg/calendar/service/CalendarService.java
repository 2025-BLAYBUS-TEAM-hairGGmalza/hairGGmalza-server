package hair.hairgg.calendar.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

public interface CalendarService {
	public String createEvent(LocalDateTime startDateTime,String email,Long reservationId) throws
		GeneralSecurityException,
		IOException;

}
