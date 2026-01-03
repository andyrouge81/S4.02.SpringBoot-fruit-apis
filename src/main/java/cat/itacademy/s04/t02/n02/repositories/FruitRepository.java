package cat.itacademy.s04.t02.n02.provider.repositories;

import cat.itacademy.s04.t02.n02.provider.model.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    boolean existsByProviderId(Long providerId);

    List<Fruit> findByProviderId(Long providerId);
}
