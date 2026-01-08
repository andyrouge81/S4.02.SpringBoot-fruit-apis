package cat.itacademy.s04.t02.n02.fruit.repositories;

import cat.itacademy.s04.t02.n02.fruit.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
