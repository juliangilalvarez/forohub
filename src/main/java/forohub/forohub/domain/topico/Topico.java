package forohub.forohub.domain.topico;


import forohub.forohub.domain.curso.Curso;
import forohub.forohub.domain.topico.records.TopicoDatosActualizar;
import forohub.forohub.domain.usuario.Usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity()
@Table( name = "topico",
        uniqueConstraints = { @UniqueConstraint(name = "ui_topico_titulo_mensaje", columnNames = {"titulo", "mensaje"}) }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String titulo;

    @Column(nullable=false, length=500)
    private String mensaje;

    @Column(nullable=false)
    private LocalDateTime fechacreacion;

    @Column(nullable=false, length=50)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechacreacion = LocalDateTime.now();
        this.estado = Estado.ABIERTO;
        this.autor = autor;
        this.curso = curso;
    }

    public void actualizar(TopicoDatosActualizar datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.estado = datos.estado();
    }

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getFechacreacion() {
        return fechacreacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }
}