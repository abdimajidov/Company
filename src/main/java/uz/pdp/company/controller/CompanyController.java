package uz.pdp.company.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Company;
import uz.pdp.company.payload.CompanyDto;
import uz.pdp.company.payload.Result;
import uz.pdp.company.service.CompanyService;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    /**
     * GET ALL COMPANIES
     * @return List<Company>
     */
    @GetMapping
    public ResponseEntity<List<Company>> getCompanies(){
        return ResponseEntity.ok(companyService.getCompanies());
    }

    /**
     * GET ONE COMPANY BY ID
     * @param id Integer
     * @return Company
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Integer id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    /**
     * ADD NEW COMPANY
     * @param companyDto CompanyDto
     * @return Result
     */
    @PostMapping
    public ResponseEntity<Result> addCompany(@Valid @RequestBody CompanyDto companyDto){
        Result result = companyService.addCompany(companyDto);
        return ResponseEntity.status(result.isSucces()?201:409).body(result);
    }

    /**
     * EDIT COMPANY BY ID
     * @param companyDto CompanyDto
     * @param id Integer
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result> editCompany(@Valid @RequestBody CompanyDto companyDto,@PathVariable Integer id){
        Result result = companyService.editCompany(companyDto, id);
        return ResponseEntity.status(result.isSucces()?202:409).body(result);
    }

    /**
     * DELETE COMPANY BY ID
     * @param id Integer
     * @return REsult
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteCompany(@PathVariable Integer id){
        Result result = companyService.deleteCompany(id);
        return ResponseEntity.status(result.isSucces()?202:409).body(result);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
