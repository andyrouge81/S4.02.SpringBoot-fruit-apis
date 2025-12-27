package cat.itacademy.s04.t02.n02.fruitMysql.model.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "providers")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 60)
    private String country;

}
