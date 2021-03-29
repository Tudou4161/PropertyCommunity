package com.service.PropertyService.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private int grade;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    //추후에 리포지토리와 서비스계층에 조회수를 자동으로 1씩 높혀주는 로직을 구성해야함.
//    @Column(columnDefinition = "bigint default 0")
    private Long findCnt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Like> like = new HashSet<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PropertyInfo propertyInfo;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //조회수를 1씩 높혀줌.
    public void setFindCnt(Long findCnt) {
        this.findCnt = findCnt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPropertyInfo(PropertyInfo propertyInfo) {
        this.propertyInfo = propertyInfo;
        propertyInfo.setPost(this);
    }

    @Builder
    public Post(Long id, String title, String content, String author, int grade,
                Address address, Category category,
                TransactionType transactionType, PropertyInfo propertyInfo, Long findCnt) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.grade = grade;
        this.address = address;
        this.category = category;
        this.propertyInfo = propertyInfo;
        this.transactionType = transactionType;
        this.findCnt = findCnt;
    }
}
