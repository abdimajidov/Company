package uz.pdp.company.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    @NotNull(message = "The company name cannot be empty")
    private String corpName;

    @NotNull(message = "The director name cannot be empty")
    private String directorName;

    @NotNull(message = "The strees name cannot be empty")
    private String street;

    @NotNull(message = "The home number cannot be empty")
    private Integer homenNumber;
}
