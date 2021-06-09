package uz.pdp.company.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    @NotNull(message = "The department name cannot be empty")
    private String name;

    @NotNull(message = "The company id cannot be empty")
    private Integer companyId;
}
