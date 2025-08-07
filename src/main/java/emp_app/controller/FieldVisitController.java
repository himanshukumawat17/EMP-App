package emp_app.controller;

import emp_app.entity.FieldVisit;
import emp_app.service.FieldVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fieldvisits")
@CrossOrigin(origins = "*")
public class FieldVisitController {

    @Autowired
    private FieldVisitService fieldVisitService;

    @PostMapping
    public FieldVisit createFieldVisit(@RequestBody FieldVisit visit) {
        return fieldVisitService.createFieldVisit(visit);
    }

    @GetMapping
    public List<FieldVisit> getAllFieldVisits() {
        return fieldVisitService.getAllVisits();
    }

    @GetMapping("/{id}")
    public Optional<FieldVisit> getFieldVisitById(@PathVariable String id) {
        return fieldVisitService.getVisitById(id);
    }

    @PutMapping
    public FieldVisit updateFieldVisit(@RequestBody FieldVisit visit) {
        return fieldVisitService.updateVisit(visit);
    }

    @DeleteMapping("/{id}")
    public void deleteFieldVisit(@PathVariable String id) {
        fieldVisitService.deleteVisit(id);
    }
}
