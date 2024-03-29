package uz.pdp.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @ManyToOne(optional = false)
    private Address address;

    @ManyToOne(optional = false)
    private Department department;
}
