package indi.huhy.archetypes.jwtsecurityweb.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;

@Slf4j
public class ResponseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void out(HttpServletResponse response, HttpStatus httpStatus) {
        out(response, httpStatus, null);
    }

    public static void out(HttpServletResponse response, Object o) {
        out(response, HttpStatus.OK, o);
    }

    public static void out(HttpServletResponse response, HttpStatus httpStatus, Object o) {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            mapper.writeValue(writer, Objects.isNull(o) ? Collections.EMPTY_MAP : o);
            writer.flush();
        } catch (IOException e) {
            log.error("Write Response Error!", e);
        }
    }
}
