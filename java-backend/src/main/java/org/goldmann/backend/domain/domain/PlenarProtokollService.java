package org.goldmann.backend.domain.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goldmann.backend.domain.domain.parlament.PlenarProtokoll;
import org.goldmann.backend.domain.domain.parlament.PlenarProtokolls;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
@AllArgsConstructor
public class PlenarProtokollService {

    public static final String API_KEY = "rgsaY4U.oZRQKUHdJhF9qguHMkwCGIoLaqEcaHjYLF";

    private final RestTemplate restTemplate;

    /*private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL).findAndRegisterModules();*/


    public ResponseEntity<PlenarProtokolls> loadProtokolls(final String start, final String end) throws URISyntaxException {
        final String url = "https://search.dip.bundestag.de/api/v1/plenarprotokoll?f.datum.start=" + start + "&f.datum.end=" + end + "&f.vorgangstyp=Gesetzgebung&format=json&apikey=" + API_KEY;
        return this.restTemplate.getForEntity(new URI(url), PlenarProtokolls.class);
    }

    public ResponseEntity<PlenarProtokoll> getProtokollById(final String id) throws URISyntaxException {
        final String url = "https://search.dip.bundestag.de/api/v1/plenarprotokoll-text?f.id=" + id + "&format=json&apikey=" + API_KEY;
        return this.restTemplate.getForEntity(new URI(url), PlenarProtokoll.class);
    }

}
