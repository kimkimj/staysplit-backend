package staysplit.hotel_reservation.domain.customer.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import staysplit.hotel_reservation.domain.common.entity.BaseEntity;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void changeEmail(String email) {
        this.email = email;
    }
}
