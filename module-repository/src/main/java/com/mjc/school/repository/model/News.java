package com.mjc.school.repository.model;

import com.mjc.school.repository.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "news")
@EntityListeners(AuditingEntityListener.class)
public class News implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Content")
    private String content;

    @Column(name = "Created", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "Updated")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Author_Id")
    private Author author;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE, CascadeType.PERSIST }
    )
    @JoinTable(
            name = "news_tags",
            joinColumns = { @JoinColumn(name = "News_Id") },
            inverseJoinColumns = { @JoinColumn(name = "Tag_Id") }
    )
    private List<Tag> tags;

    @OneToMany(
            mappedBy = "news",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<Comment> comments;

}
