package staysplit.hotel_reservation.review.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private Long hotel_id;

    @Column(nullable = false)
    private String content;

    //점수포맷이 100점인지 5점인지
    @Column(nullable = false)
    private int rate;

}
