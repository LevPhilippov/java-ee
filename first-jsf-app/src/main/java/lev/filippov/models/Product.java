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
@NamedEntityGraph(name = "products-with-categories-and-brands",attributeNodes = {@NamedAttributeNode("category"), @NamedAttributeNode("brand")})
public class Product implements Serializable {

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId", referencedColumnName = "id")
    private Brand brand;
}
