package adnavas.fitlux_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "usuarios")
public class Usuario {
    @MongoId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;

    @Indexed(unique = true)
    @NotEmpty(message = "El usuario debe tener un username")
    private String username;

    @NotEmpty(message = "El usuario debe tener un email")
    @Indexed(unique = true)
    private String email;

    private String password;

    @NotEmpty(message = "El usuario debe tener un dni")
    @Indexed(unique = true)
    private String dni;

    @NotEmpty(message = "El usuario debe tener nombre y apellidos")
    private String fullname;

    private String role;

    private boolean enabled;

    public Usuario(String username, String email, String password, String dni, String fullname, String role, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.fullname = fullname;
        this.role = role;
        this.enabled = enabled;
    }
}