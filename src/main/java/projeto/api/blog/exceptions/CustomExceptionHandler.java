package projeto.api.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
    
    private Map<String,String> erros;

    private HttpHeaders headers;

    CustomExceptionHandler() {
        erros = new HashMap<>();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @ExceptionHandler({RuntimeException.class,BadCredentialsException.class})
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException runtime, WebRequest req) {
        runtime.printStackTrace();
        erros.put("ERROR",runtime.getMessage());

        return handleExceptionInternal(runtime, erros, headers, HttpStatus.BAD_REQUEST, req);
    }
}
