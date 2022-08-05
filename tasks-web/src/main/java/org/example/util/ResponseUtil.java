package org.example.web.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ResponseUtil {
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, (HttpHeaders) null);
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse
                .map(r -> response(r, header))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    static <X> ResponseEntity<X> response(X r, HttpHeaders header) {
        return ((ResponseEntity.BodyBuilder) ResponseEntity.ok().headers(header)).body(r);
    }
}
