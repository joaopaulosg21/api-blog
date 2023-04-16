package projeto.api.blog.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.api.blog.model.DTO.UserDTO;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be null")
    private String title;

    @NotBlank(message = "Content cannot be null")
    private String content;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creation_date;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate edit_date;

    private boolean published;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id", referencedColumnName = "id")
    private User user;

    public UserDTO getUser() {
        return user.toDTO();
    }
}
