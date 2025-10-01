package idmusic.modulo_jwt.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private Integer id_usuario;
    private String nombre;
    private String apellido_paterno;
    private String correo;
    private String password;

}
