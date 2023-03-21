package api.chaehwi.ruheeblog.tops;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopsService {
    @Autowired
    private TopsRepository topsRepository;

    public Tops save(String searchWord) {
        Tops tops = findById(searchWord);
        if (tops == null) {
            tops = Tops.builder()
                    .searchWord(searchWord)
                    .searchCount(1)
                    .build();
        } else {
            tops.setSearchCount(tops.getSearchCount() + 1);
        }
        return topsRepository.save(tops);
    }

    public Tops findById(String searchWord) {
        Optional<Tops> optionalTops = topsRepository.findById(searchWord);
        return optionalTops.orElse(null);
    }

    public List<Tops> findTop10() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "searchCount"));
        Page<Tops> all = topsRepository.findAll(pageable);
        List<Tops> result = all.get().collect(Collectors.toList());
        return result;
    }
}
