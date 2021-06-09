package uz.pdp.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Company;
import uz.pdp.company.entity.Department;
import uz.pdp.company.payload.DepartmentDto;
import uz.pdp.company.payload.Result;
import uz.pdp.company.repository.CompanyRepository;
import uz.pdp.company.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    /**
     * GET ALL DEPARTMENTS
     * @return List<DZepartment>
     */
    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    /**
     * GET DEPARTMENT BY ID
     * @param id Integer
     * @return Department
     */
    public Department getDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    /**
     * ADD NEW DEPARTMENT
     * @param departmentDto DepartmentDto
     * @return Result
     */
    public Result addDepartment(DepartmentDto departmentDto){
        boolean existsByNameAndCompanyId = departmentRepository.
                existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if(existsByNameAndCompanyId) return
                new Result("All ready exist Department name and Company id",false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if(!optionalCompany.isPresent()) return new Result("Not found company",false);

        Department department=new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new Result("Successfully added", true);

    }

    /**
     * EDIT DEPARTMETN BY ID
     * @param departmentDto DepartmentDto
     * @param id Integer
     * @return Result
     */
    public Result editDepartment(DepartmentDto departmentDto,Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if(!optionalDepartment.isPresent()) return new Result("not found Department",false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if(!optionalCompany.isPresent()) return new Result("Not found Company",false);

        boolean exists = departmentRepository.existsByNameAndCompanyIdAndIdNot(
                departmentDto.getName(), departmentDto.getCompanyId(), id
        );
        if(exists) return new
                Result("All ready exist Department Name with Company name",false);

        Department department = optionalDepartment.get();

        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new Result("Successfully edited",true);


    }

    /**
     * DELETE DEPARTMENT BY ID
     * @param id Integer
     * @return Result
     */
    public Result deleteDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if(!optionalDepartment.isPresent()) return new Result("Not found Department",false);
        departmentRepository.deleteById(id);
        return new Result("Successfully deleted",true);
    }
}
