package idmusic.modulo_jwt.exception;

public class Excepcion {

    public static class UserNotFound extends RuntimeException {
        public UserNotFound(String correo) {
            super("Usuario no encontrado: " + correo);
        }
    }
    public static class DataBaseException extends RuntimeException{
        public  DataBaseException(String msg){
            super(msg);
        }
    }
    public static class AuthException extends RuntimeException {
        public AuthException(String mensaje) {
            super(mensaje);
        }
    }
}
