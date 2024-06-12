package adnavas.fitlux_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "clases")
public class Clase {
    @MongoId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;

    private LocalDateTime fechainicio;

    private LocalDateTime fechafin;

    @DBRef
    @JsonIgnoreProperties(value = {"password"})
    private Usuario profesor;

    @DBRef
    @JsonIgnoreProperties(value = {"password"})
    private List<Usuario> usuarios;

    @DBRef
    private Actividad actividad;

    @DBRef
    private Sala sala;

    private boolean activa;

}
