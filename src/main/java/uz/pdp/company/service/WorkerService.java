package uz.pdp.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Department;
import uz.pdp.company.entity.Worker;
import uz.pdp.company.payload.Result;
import uz.pdp.company.payload.WorkerDto;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.repository.CompanyRepository;
import uz.pdp.company.repository.DepartmentRepository;
import uz.pdp.company.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;

    /**
     * GET ALL WORKERS
     * @return List<Worker>
     */
    public List<Worker> getWorkers(){
       return workerRepository.findAll();
    }

    /**
     * GET WORKER BY ID
     * @param id Integer
     * @return Worker
     */
    public Worker getWorker(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    /**
     * ADD NEW WORKER
     * @param workerDto WorkerDto
     * @return Result
     */
    public Result addWorker(WorkerDto workerDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if(!optionalDepartment.isPresent()) return new Result("not found Department",false);

        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if(existsByPhoneNumber) return new Result("Phone number all ready exist ",false);

        Worker worker=new Worker();
        worker.setDepartment(optionalDepartment.get());
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Address address=new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        worker.setAddress(savedAddress);

        workerRepository.save(worker);
        return new Result("Successfully added",true);
    }

    /**
     * EDIT WORKER BY ID
     * @param workerDto WorkerDto
     * @param id Integer
     * @return Result
     */
    public Result editWorker(WorkerDto workerDto,Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if(!optionalWorker.isPresent()) return new Result("Not found worker",false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if(!optionalDepartment.isPresent()) return new Result("Not found Department",false);

        boolean existsByPhoneNumberAndIdNot = workerRepository.
                existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if(existsByPhoneNumberAndIdNot) return new Result("Phone number all ready exist",false);

        Worker worker = optionalWorker.get();
        worker.setDepartment(optionalDepartment.get());

        Address address = worker.getAddress();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        worker.setAddress(savedAddress);
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        workerRepository.save(worker);
        return new Result("Successfully added", true);


    }

    /**
     * DELETE WORKER
     * @param id Integer
     * @return Result
     */
    public Result deleteWorker(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if(!optionalWorker.isPresent()) return new Result("Not found worker",false);
        workerRepository.deleteById(id);
        return new Result("Successfully deleted",true);
    }
}
