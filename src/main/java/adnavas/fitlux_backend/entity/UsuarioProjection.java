package adnavas.fitlux_backend.entity;

public interface UsuarioProjection {

    String get_id();
    String getUsername();
    String getEmail();
    String getDni();
    String getFullname();
    String getRole();
    boolean getEnabled();

}
