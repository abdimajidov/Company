package uz.pdp.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.company.entity.Address;
import uz.pdp.company.entity.Company;
import uz.pdp.company.payload.CompanyDto;
import uz.pdp.company.payload.Result;
import uz.pdp.company.repository.AddressRepository;
import uz.pdp.company.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    /**
     * GET ALL COMPANIES
     * @return List<Company>
     */
    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    /**
     * GET ONE COMPANY BY ID
     * @param id Integer
     * @return Company
     */
    public Company getCompany(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    /**
     * ADD NEW COMPANY
     * @param companyDto CompanyDto
     * @return Result
     */
    public Result addCompany(CompanyDto companyDto){
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if(existsByCorpName) return new Result("All ready Exists Company name",false);

        Address address=new Address();
        address.setHomeNumber(companyDto.getHomenNumber());
        address.setStreet(companyDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        Company company=new Company();
        company.setAddress(savedAddress);
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);

        return new Result("Company added",true);

    }

    /**
     * EDIT COMPANY BY ID
     * @param companyDto CompanyDto
     * @param id Integer
     * @return Result
     */
    public Result editCompany(CompanyDto companyDto,Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(!optionalCompany.isPresent()) return
                new Result("Not found company",false);

        boolean existsByCorpNameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if(existsByCorpNameAndIdNot) return
                new Result("All ready exist Company name",false);

        Company company = optionalCompany.get();

        Address address = company.getAddress();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomenNumber());
        Address savedAddress = addressRepository.save(address);

        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(savedAddress);
        company.setCorpName(companyDto.getCorpName());

        companyRepository.save(company);
        return new Result("Successfully edited company",true);
    }

    /**
     * DELETE COMPANY BY ID
     * @param id Integer
     * @return REsult
     */
    public Result deleteCompany(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(!optionalCompany.isPresent()) return new Result("Not found company",false);
        companyRepository.deleteById(id);
        return new Result("Successfully deleted",true);
    }

}
