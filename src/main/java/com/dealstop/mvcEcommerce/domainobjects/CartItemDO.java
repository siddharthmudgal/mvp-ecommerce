package com.dealstop.mvcEcommerce.domainobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "cartitem")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CartItemDO {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String uuid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id")
    CartDO cart_id;

    @OneToOne
    ProductDO productDO;

    @Column
    Long quantity;

    @JsonIgnore
    @CreatedDate
    Instant createdOn;

    @JsonIgnore
    @LastModifiedDate
    Instant modifiedOn;
}
