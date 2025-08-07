package emp_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emp_app.entity.Lead;
import emp_app.service.LeadService;
import emp_app.utils.ResponseWrapper;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "*")
public class LeadController {

    @Autowired
    private LeadService leadService;

  
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<?>> createLead(@RequestBody Lead lead) {
        return ResponseEntity.ok(leadService.createLead(lead));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<?>> getAllLeads() {
        return ResponseEntity.ok(leadService.getAllLeads());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseWrapper<?>> getLeadById(@PathVariable String id) {
        return ResponseEntity.ok(leadService.getLeadById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper<?>> updateLead(
            @PathVariable String id,
            @RequestBody Lead lead) {
        lead.setId(id);
        return ResponseEntity.ok(leadService.updateLead(lead));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<?>> deleteLead(@PathVariable String id) {
        return ResponseEntity.ok(leadService.deleteLead(id));
    }
}
