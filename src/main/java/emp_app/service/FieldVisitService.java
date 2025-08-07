package emp_app.service;

import emp_app.entity.FieldVisit;

import java.util.List;
import java.util.Optional;

public interface FieldVisitService {

    FieldVisit createFieldVisit(FieldVisit visit);

    List<FieldVisit> getAllVisits();

    Optional<FieldVisit> getVisitById(String id);

    FieldVisit updateVisit(FieldVisit visit);

    void deleteVisit(String id);
}
