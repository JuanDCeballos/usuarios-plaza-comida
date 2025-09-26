package co.juan.plazacomidas.model.utils;

public enum MensajesEnum {
    PROPIETARIO_MAYOR_EDAD("El propietario debe ser mayor de edad."),
    CORREO_YA_REGISTRADO("El correo ya se encuentra registrado en la aplicación."),
    ROL_NO_ENCONTRADO_POR_NOMBRE("Rol no encontrado con el nombre: "),
    USUARIO_NO_ENCONTRADO_POR_ID("Usuario no encontrado con el id: "),
    USUARIO_NO_ENCONTRADO_POR_CORREO("Usuario no encontrado con el correo: "),
    ID_POSITIVO("El id del usuario debe ser un número positivo.");


    private String mensaje;

    private MensajesEnum(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
