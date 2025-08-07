package emp_app.service;

import emp_app.entity.Lead;
import emp_app.utils.ResponseWrapper;

public interface LeadService {
    ResponseWrapper<?> createLead(Lead lead);

    ResponseWrapper<?> getAllLeads();

    ResponseWrapper<?> getLeadById(String id);

    ResponseWrapper<?> updateLead(Lead lead);

    ResponseWrapper<?> deleteLead(String id);
}

