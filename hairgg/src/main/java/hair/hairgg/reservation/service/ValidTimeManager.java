package hair.hairgg.reservation.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidTimeManager {
	private static final int START_HOUR = 10;
	private static final int END_HOUR = 20;
	private static final int INTERVER_MIN = 30;

	public static List<LocalTime> getValidTimes(List<LocalDateTime> reservedTimes) {
		List<LocalTime> validTimes = new ArrayList<>();
		LocalTime time = LocalTime.of(START_HOUR, 0);
		while(time.getHour() < END_HOUR) {
			if(!isReservedTime(reservedTimes, time)) {
				validTimes.add(time);
			}
			time = time.plusMinutes(INTERVER_MIN);
		}
		return validTimes;
	}

	//TODO: 나중에 리팩토링 고민..
	private static boolean isReservedTime(List<LocalDateTime> reservedTimes, LocalTime time) {
		for(LocalDateTime reservedTime : reservedTimes) {
			if(reservedTime.getHour() == time.getHour() && reservedTime.getMinute() == time.getMinute()) {
				return true;
			}
		}
		return false;
	}
}
