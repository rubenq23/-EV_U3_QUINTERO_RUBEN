package ipss.TechSolutions.model;

import jakarta.persistence.*;

@Entity //le indica a spring que esta clase sera una tabla de la base de datos de MySQL
@Table(name = "usuarios") //indica el nombre de la tabla en la base de datos
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String clave;

    // Getters y Setters necesarios para que Spring pueda leer y guardar los datos, de cualquier manera uso lombok
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
