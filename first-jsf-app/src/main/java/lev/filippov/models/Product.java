package lev.filippov.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId", referencedColumnName = "id")
    private Brand brand;
}