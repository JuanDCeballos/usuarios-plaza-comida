package co.juan.plazacomidas.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "El documento de identidad es obligatorio")
    @Positive(message = "El documento de identidad debe ser un número positivo")
    private Long documentoDeIdentidad;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^(\\d{10}|\\+\\d{12})$", message = "El número de celular debe tener 10 dígitos o incluir el prefijo internacional (ej. +57) seguido de 10 dígitos.")
    private String celular;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    private String correo;

    @NotBlank(message = "La clave es obligatoria")
    private String clave;
}
