package uz.pdp.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Department;
import uz.pdp.company.payload.DepartmentDto;
import uz.pdp.company.payload.Result;
import uz.pdp.company.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departmetn")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    /**
     * GET ALL DEPARTMENTS
     * @return List<DZepartment>
     */
    @GetMapping
    public ResponseEntity<List<Department>> getDepartments(){
        return ResponseEntity.ok(departmentService.getDepartments());
    }

    /**
     * GET DEPARTMENT BY ID
     * @param id Integer
     * @return Department
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Integer id){
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    /**
     * ADD NEW DEPARTMENT
     * @param departmentDto DepartmentDto
     * @return Result
     */
    @PostMapping
    public ResponseEntity<Result> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        Result result = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(result.isSucces()?201:409).body(result);
    }

    /**
     * EDIT DEPARTMETN BY ID
     * @param departmentDto DepartmentDto
     * @param id Integer
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result> editDepartment(@Valid @RequestBody DepartmentDto departmentDto,@PathVariable Integer id){
        Result result = departmentService.editDepartment(departmentDto, id);
        return ResponseEntity.status(result.isSucces()?202:409).body(result);

    }

    /**
     * DELETE DEPARTMENT BY ID
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteDepartment(@PathVariable Integer id){
        Result result = departmentService.deleteDepartment(id);
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
