package hair.hairgg.calendar.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import hair.hairgg.calendar.GoogleCalendarConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleCalendarService implements CalendarService {

	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_EVENTS,
		CalendarScopes.CALENDAR_EVENTS);
	private final GoogleCalendarConfig googleCalendarConfig;

	@Autowired
	public GoogleCalendarService(GoogleCalendarConfig googleCalendarConfig) {
		this.googleCalendarConfig = googleCalendarConfig;
	}


	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
		throws IOException {

		GoogleClientSecrets.Details details = new GoogleClientSecrets.Details()
			.setClientId(googleCalendarConfig.getClientId())
			.setClientSecret(googleCalendarConfig.getClientSecret())
			.setRedirectUris(googleCalendarConfig.getRedirectUris());

		GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setInstalled(details);

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
			HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
			.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(googleCalendarConfig.getTokenDir())))
			.setAccessType("offline")
			.build();
		log.info("flow : {}", flow);
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		log.info("receiver : {}", receiver);
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		log.info("credential : {}", credential);

		return credential;
	}

	@Override
	public String createEvent(LocalDateTime startDateTime, String email,
		Long reservationId) throws GeneralSecurityException, IOException {
		Calendar service = getCalendarService();
		String timeZone = "Asia/Seoul";
		Event event = new Event()
			.setSummary("화상 컨설팅-Haertz")
			.setDescription("Haertz 화상 헤어 컨설팅입니다.");

		setEventTime(startDateTime, event, timeZone);

		EventAttendee attendee = new EventAttendee().setEmail(email);
		log.info("attendee : {}", attendee);
		event.setAttendees(Collections.singletonList(attendee));

		ConferenceData conferenceData = new ConferenceData();
		CreateConferenceRequest createConferenceRequest = new CreateConferenceRequest();
		createConferenceRequest.setRequestId(reservationId.toString());
		conferenceData.setCreateRequest(createConferenceRequest);
		event.setConferenceData(conferenceData);

		String calendarId = "primary";
		event = service.events().insert(calendarId, event).setConferenceDataVersion(1).execute();

		return event.getHangoutLink();
	}

	private void setEventTime(LocalDateTime startDateTime, Event event, String timeZone) {
		DateTime start = new DateTime(startDateTime +":00+09:00");
		event.setStart(new EventDateTime().setDateTime(start)
			.setTimeZone(timeZone));
		//마감 시간은 30분뒤로 설정
		LocalDateTime temp = startDateTime.plusMinutes(30);
		DateTime end = new DateTime(temp +":00+09:00");
		event.setEnd(new EventDateTime().setDateTime(end)
			.setTimeZone(timeZone));
	}

	private Calendar getCalendarService() throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
			.setApplicationName(googleCalendarConfig.getApplicationName())
			.build();
	}
}
