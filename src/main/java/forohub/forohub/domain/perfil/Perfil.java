package forohub.forohub.domain.perfil;

import forohub.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity()
@Table( name = "perfil",
        uniqueConstraints = { @UniqueConstraint(name = "ui_perfil_nombre", columnNames = {"nombre"}) }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String nombre;

    @ManyToMany(mappedBy = "perfiles", fetch = FetchType.LAZY)
    @OrderBy("nombre ASC")
    private List<Usuario> usuarios = new ArrayList<>();

    public Long getId() {
        return id;
    }
}