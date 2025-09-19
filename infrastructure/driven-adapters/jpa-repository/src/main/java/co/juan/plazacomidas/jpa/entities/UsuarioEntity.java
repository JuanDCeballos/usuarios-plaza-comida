package co.juan.plazacomidas.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;
    private String apellido;
    private Long documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;
}
