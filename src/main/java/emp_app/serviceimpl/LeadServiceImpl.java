package emp_app.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import emp_app.entity.EMPEntity;
import emp_app.entity.FieldVisit;
import emp_app.entity.Lead;
import emp_app.respository.EMPRepository;
import emp_app.respository.FieldVisitRepository;
import emp_app.respository.LeadRepository;
import emp_app.service.LeadService;
import emp_app.utils.ResponseWrapper;

@Service
public class LeadServiceImpl implements LeadService {

	private final LeadRepository leadRepository;
	private final FieldVisitRepository fieldVisitRepository;
	private final EMPRepository employeeRepository;

	public LeadServiceImpl(LeadRepository leadRepository, FieldVisitRepository fieldVisitRepository,
			EMPRepository employeeRepository) {
		this.leadRepository = leadRepository;
		this.fieldVisitRepository = fieldVisitRepository;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public ResponseWrapper<?> getAllLeads() {
		List<Lead> leads = leadRepository.findAll();
		return new ResponseWrapper<>("Leads fetched successfully", true, leads);
	}

	@Override
	public ResponseWrapper<?> getLeadById(String id) {
		Optional<Lead> lead = leadRepository.findById(id);
		if (lead.isPresent()) {
			return new ResponseWrapper<>("Lead found", true, lead.get());
		}
		return new ResponseWrapper<>("Lead not found with ID: " + id, false, null);
	}

	@Override
	public ResponseWrapper<?> updateLead(Lead lead) {
		Optional<Lead> existing = leadRepository.findById(lead.getId());
		if (existing.isPresent()) {
			Lead l = existing.get();

			boolean isConverted = !"converted".equalsIgnoreCase(l.getStatus())
					&& "converted".equalsIgnoreCase(lead.getStatus());

			l.setTitle(lead.getTitle());
			l.setDescription(lead.getDescription());
			l.setImage(lead.getImage());
			l.setRemark(lead.getRemark());
			l.setAssignTo(lead.getAssignTo());
			l.setStatus(lead.getStatus());
			l.setUpdatedDate(new Date());

			Lead updatedLead = leadRepository.save(l);

			if (isConverted) {
				Optional<EMPEntity> employeeOpt = employeeRepository.findByUserName(l.getAssignTo());
				if (employeeOpt.isPresent()) {
					EMPEntity employee = employeeOpt.get();

					FieldVisit visit = new FieldVisit();
					visit.setLeadId(updatedLead.getId());
					visit.setAssignTo(employee.getId());
					visit.setStatus("open");
					visit.setCreatedDate(new Date());
					visit.setUpdatedDate(new Date());

					fieldVisitRepository.save(visit);
				}
			}

			return new ResponseWrapper<>("Lead updated successfully", true, updatedLead);
		}

		return new ResponseWrapper<>("Lead not found with ID: " + lead.getId(), false, null);
	}

	@Override
	public ResponseWrapper<?> deleteLead(String id) {
		if (leadRepository.existsById(id)) {
			leadRepository.deleteById(id);
			return new ResponseWrapper<>("Lead deleted successfully", true, null);
		}
		return new ResponseWrapper<>("Lead not found with ID: " + id, false, null);
	}

	@Override
	public ResponseWrapper<?> createLead(Lead lead) {

		// Convert assignTo from username to employee ID if provided
		if (lead.getAssignTo() != null && !lead.getAssignTo().isEmpty()) {
			Optional<EMPEntity> empOptional = employeeRepository.findByUserName(lead.getAssignTo());
			if (empOptional.isPresent()) {
				lead.setAssignTo(empOptional.get().getId());
			} else {
				return new ResponseWrapper<>("User not found with username: " + lead.getAssignTo(), false, null);
			}
		}

		// Default status to "open" if not provided
		if (lead.getStatus() == null || lead.getStatus().isEmpty()) {
			lead.setStatus("open");
		}

		lead.setCreatedDate(new Date());
		lead.setUpdatedDate(new Date());

		// Save lead first to generate ID
		Lead savedLead = leadRepository.save(lead);

		// If status is 'converted' or 'sent', create corresponding FieldVisit
		if ("converted".equalsIgnoreCase(savedLead.getStatus()) || "sent".equalsIgnoreCase(savedLead.getStatus())) {
			FieldVisit fieldVisit = new FieldVisit();
			fieldVisit.setLeadId(savedLead.getId());
			fieldVisit.setStatus("open");
			fieldVisit.setCreatedDate(new Date());
			fieldVisit.setUpdatedDate(new Date());

			// Set assignTo in FieldVisit if lead has assignTo (already converted to
			// employee ID)
			if (savedLead.getAssignTo() != null && !savedLead.getAssignTo().isEmpty()) {
				fieldVisit.setAssignTo(savedLead.getAssignTo());
			}

			fieldVisitRepository.save(fieldVisit);
		}

		return new ResponseWrapper<>("Lead created successfully", true, savedLead);
	}
}