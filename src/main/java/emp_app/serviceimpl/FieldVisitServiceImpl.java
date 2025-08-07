package emp_app.serviceimpl;

import emp_app.entity.FieldVisit;
import emp_app.entity.Lead;
import emp_app.respository.FieldVisitRepository;
import emp_app.respository.LeadRepository;
import emp_app.service.FieldVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FieldVisitServiceImpl implements FieldVisitService {

    @Autowired
    private FieldVisitRepository visitRepository;

    @Autowired
    private LeadRepository leadRepository;

    @Override
    public FieldVisit createFieldVisit(FieldVisit visit) {
        visit.setStatus("open");
        visit.setCreatedDate(new Date());
        visit.setUpdatedDate(new Date());
        return visitRepository.save(visit);
    }

    @Override
    public List<FieldVisit> getAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public Optional<FieldVisit> getVisitById(String id) {
        return visitRepository.findById(id);
    }

    @Override
    public FieldVisit updateVisit(FieldVisit visit) {
        Optional<FieldVisit> existing = visitRepository.findById(visit.getId());
        if (existing.isPresent()) {
            FieldVisit v = existing.get();
            v.setLeadId(visit.getLeadId());
            v.setAssignTo(visit.getAssignTo());
            v.setStatus(visit.getStatus());
            v.setUpdatedDate(new Date());

            // Update corresponding Lead status
            Optional<Lead> leadOptional = leadRepository.findById(visit.getLeadId());
            leadOptional.ifPresent(lead -> {
                lead.setStatus(visit.getStatus());
                lead.setUpdatedDate(new Date());
                leadRepository.save(lead);
            });

            return visitRepository.save(v);
        }
        return null;
    }

    @Override
    public void deleteVisit(String id) {
        visitRepository.deleteById(id);
    }
}
