package com.service.PropertyService.domain;
import lombok.*;
import javax.jdo.annotations.Join;
import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyInfo extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "propertyinfo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private double latitude;
    private double longitude;

    @Builder
    public PropertyInfo(Post post, double latitude, double longitude) {
        this.post = post;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
