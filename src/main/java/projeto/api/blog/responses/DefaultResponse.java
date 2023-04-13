package projeto.api.blog.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class DefaultResponse {
    private String status;

    private String message;
    
}
