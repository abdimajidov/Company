package uz.pdp.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.company.entity.Worker;
import uz.pdp.company.payload.Result;
import uz.pdp.company.payload.WorkerDto;
import uz.pdp.company.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    /**
     * GET ALL WORKERS
     * @return List<Worker>
     */
    @GetMapping
    public ResponseEntity<List<Worker>> getWorkers(){
        return ResponseEntity.ok(workerService.getWorkers());
    }

    /**
     * GET WORKER BY ID
     * @param id Integer
     * @return Worker
     */
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Integer id){
        return ResponseEntity.ok(workerService.getWorker(id));
    }

    /**
     * ADD NEW WORKER
     * @param workerDto WorkerDto
     * @return Result
     */
    @PostMapping
    public ResponseEntity<Result> addWorker(@Valid @RequestBody WorkerDto workerDto){
        Result result = workerService.addWorker(workerDto);
        return ResponseEntity.status(result.isSucces()?201:409).body(result);
    }

    /**
     * EDIT WORKER BY ID
     * @param workerDto WorkerDto
     * @param id Integer
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result> editWorker(@Valid @RequestBody WorkerDto workerDto, @PathVariable Integer id){
        Result result = workerService.editWorker(workerDto, id);
        return ResponseEntity.status(result.isSucces()?202:409).body(result);
    }

    /**
     * DELETE WORKER
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteWorker(@PathVariable Integer id){
        Result result = workerService.deleteWorker(id);
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
