package uz.pdp.company.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {
    @NotNull(message = "The name cannot be empty")
    private String name;

    @NotNull(message = "The phoen number cannot be empty")
    private String phoneNumber;

    @NotNull(message = "The street name cannot be empty")
    private String street;

    @NotNull(message = "The home number cannot be empty")
    private Integer homeNumber;

    @NotNull(message = "The department id cannot be empty")
    private Integer departmentId;
}
