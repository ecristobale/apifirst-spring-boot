package com.ecristobale.apifirst.apifirstspringboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;

    @Embedded
    private Dimensions dimensions;

    @ManyToMany
    private List<Category> categories;

    @NotNull
    @Size(min=3,max=255)
    private String description;

    @NotNull
    @Pattern(regexp="^-?(?:0|[1-9]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$")
    private String price;

    @Pattern(regexp="^-?(?:0|[1-9]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$")
    private String cost;

    @CreationTimestamp
    private OffsetDateTime dateCreated;

    @UpdateTimestamp
    private OffsetDateTime dateUpdated;

    public static class ProductBuilder {
        public Product build() {
            Product product = new Product(this.id, this.images, this.dimensions, this.categories, this.description, this.price, this.cost, this.dateCreated, this.dateUpdated);

            if(this.images != null) {
                this.images.forEach(i -> i.setProduct(product));
            }

            return product;
        }
    }
}
