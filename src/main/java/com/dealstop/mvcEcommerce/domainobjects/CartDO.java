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
import java.util.List;

@Entity
@Table(name = "cart")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CartDO {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String uuid;

    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    UserDO user;

    @OneToMany(mappedBy = "cart_id", cascade = CascadeType.REMOVE)
    List<CartItemDO> cartItemDO;

    @JsonIgnore
    @CreatedDate
    Instant createdOn;

    @JsonIgnore
    @LastModifiedDate
    Instant modifiedOn;

}
