package api.chaehwi.ruheeblog.tops;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TopsRepository extends JpaRepository<Tops, String> {
    @Override
    @Transactional
    Tops save(Tops entity);

    @Modifying
    @Query("UPDATE Tops t SET t.searchCount = t.searchCount + 1 WHERE t.searchWord = :searchWord")
    int updateSearchCount(String searchWord);

    @Override
    Optional<Tops> findById(String searchWord);

    @Override
    Page<Tops> findAll(Pageable pageable);

}
