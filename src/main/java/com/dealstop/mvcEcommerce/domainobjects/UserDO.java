package com.dealstop.mvcEcommerce.domainobjects;

import com.dealstop.mvcEcommerce.enums.UserTypeEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table (name = "users")
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

    @Column
    UserTypeEnum userType;

    @CreatedDate
    Instant createdOn;

    @LastModifiedDate
    Instant modifiedOn;

}
