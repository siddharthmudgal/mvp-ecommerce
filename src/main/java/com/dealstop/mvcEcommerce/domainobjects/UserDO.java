package com.dealstop.mvcEcommerce.domainobjects;

import com.dealstop.mvcEcommerce.enums.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table (name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class UserDO {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String uuid;

    @Column
    String username;

    @Enumerated(EnumType.STRING)
    UserTypeEnum userType;

    @JsonIgnore
    @CreatedDate
    Instant createdOn;

    @JsonIgnore
    @LastModifiedDate
    Instant modifiedOn;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn
    CartDO cart;

}
