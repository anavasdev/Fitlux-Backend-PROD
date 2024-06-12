package adnavas.fitlux_backend.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "salas")
public class Sala {
    @MongoId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;

    private String nombre;
    private int aforo;
}
