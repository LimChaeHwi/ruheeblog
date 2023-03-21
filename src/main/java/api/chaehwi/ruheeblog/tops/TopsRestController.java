package api.chaehwi.ruheeblog.tops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tops")
public class TopsRestController {
    private final TopsService topsService;

    public TopsRestController(TopsService topsService) {
        this.topsService = topsService;
    }

    @PostMapping(value="/save")
    public Tops save(String searchWord) {
        return topsService.save(searchWord);
    }

    @GetMapping(value="/ten")
    public List<Tops> findTop10() {
        return topsService.findTop10();
    }
}
