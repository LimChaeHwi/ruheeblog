package api.chaehwi.ruheeblog.tops;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TopsRepository extends JpaRepository<Tops, String> {
    @Override
    @Transactional
    Tops save(Tops entity);

    @Override
    Optional<Tops> findById(String searchWord);

    @Override
    Page<Tops> findAll(Pageable pageable);

}
