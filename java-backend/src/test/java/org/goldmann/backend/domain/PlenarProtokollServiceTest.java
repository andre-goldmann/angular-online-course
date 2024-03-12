package org.goldmann.backend.domain;

import io.micrometer.core.instrument.util.IOUtils;
import org.goldmann.backend.config.WebConfig;
import org.goldmann.backend.domain.domain.PlenarProtokollService;
import org.goldmann.backend.domain.domain.parlament.PlenarProtokolls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.matomo.java.tracking.TrackerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

import static org.goldmann.backend.domain.domain.PlenarProtokollService.API_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@Import(WebConfig.class)
@RestClientTest(PlenarProtokollService.class)
class PlenarProtokollServiceTest {

    PlenarProtokollService cut;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private MockRestServiceServer server;

    @Autowired
    RestTemplateBuilder builder;

    private RestTemplate restTemplate;

    @MockBean
    private TrackerConfiguration trackerConfiguration;

    @MockBean
    private org.matomo.java.tracking.MatomoTracker matomoTracker;

    @BeforeEach
    void setUp() {
        this.restTemplate = builder.build();
        this.cut = new PlenarProtokollService(restTemplate);
        this.server= MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void loadProtokollsEmpty() throws URISyntaxException {
        String start = "2022-01-11";
        String end ="2023-01-15";

        this.server.expect(requestTo("https://search.dip.bundestag.de/api/v1/plenarprotokoll?f.datum.start=" + start + "&f.datum.end=" + end + "&f.vorgangstyp=Gesetzgebung&format=json&apikey=" + API_KEY))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        this.cut.loadProtokolls(start, end);
    }

    @Test
    void loadProtokolls() throws URISyntaxException {
        // Setup
        final String start = "2022-01-11";
        final String end ="2023-01-15";
        final String json = IOUtils.toString(
                this.getClass().getResourceAsStream("protokolle.json"));
        assertTrue(!json.isEmpty());

        // Execute
        this.server.expect(requestTo("https://search.dip.bundestag.de/api/v1/plenarprotokoll?f.datum.start=" + start + "&f.datum.end=" + end + "&f.vorgangstyp=Gesetzgebung&format=json&apikey=" + API_KEY))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        // Verify
        final PlenarProtokolls protokolls = this.cut.loadProtokolls(start, end).getBody();
        assertEquals(77, protokolls.getDocuments().size());
    }

    @Test
    void loadProtokoll() throws URISyntaxException {
        // Setup
        final String id = "";
        final String json = IOUtils.toString(
                this.getClass().getResourceAsStream("protokoll.json"));
        assertTrue(!json.isEmpty());

        // Execute
        this.server
                .expect(requestTo("https://search.dip.bundestag.de/api/v1/plenarprotokoll-text?f.id=" + id + "&format=json&apikey=" + API_KEY))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        // Verify
        assertNotNull(this.cut.getProtokollById(id));
    }
}