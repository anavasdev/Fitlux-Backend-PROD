package adnavas.fitlux_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "avisos")
public class Aviso {
    @MongoId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;

    @DBRef
    @JsonIgnoreProperties(value = {"password"})
    private List<Usuario> usuarios;

    private String mensaje;

    public Aviso(List<Usuario> usuarios, String mensaje) {
        this.usuarios = usuarios;
        this.mensaje = mensaje;
    }
}
