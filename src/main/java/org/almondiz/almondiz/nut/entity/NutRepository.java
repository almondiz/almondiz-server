package org.almondiz.almondiz.nut.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutRepository extends JpaRepository<Nut, Long> {

    Optional<Nut> findById(Long nutId);

    Optional<Nut> findByNutName(String nutName);

}
