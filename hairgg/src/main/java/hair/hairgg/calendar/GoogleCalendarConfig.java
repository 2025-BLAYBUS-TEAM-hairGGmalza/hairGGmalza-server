package hair.hairgg.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Configuration

public class GoogleCalendarConfig {

	@Value("${google.calendar.client-id}")
	private String clientId;

	@Value("${google.calendar.client-secret}")
	private String clientSecret;

	@Value("${google.calendar.project-id}")
	private String applicationName;

	@Value("${google.calendar.tokens-directory}")
	private String tokenDir;

	@Value("#{'${google.calendar.redirect-uris}'.split(',')}")
	private List<String> redirectUris;

	public String test() {
		return applicationName+"-hairgg";
	}
}