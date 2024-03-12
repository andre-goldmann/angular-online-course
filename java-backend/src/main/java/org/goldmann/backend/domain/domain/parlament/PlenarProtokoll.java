package org.goldmann.backend.domain.domain.parlament;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class PlenarProtokoll {
    Long id;

    String dokumentart;

    String typ;

    String dokumentnummer;

    String wahlperiode;

    OffsetDateTime aktualisiert;

}
