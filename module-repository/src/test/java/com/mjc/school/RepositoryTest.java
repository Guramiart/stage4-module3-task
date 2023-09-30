package com.mjc.school;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.config.JpaRepositoryConfig;
import com.mjc.school.repository.filter.pagination.Page;
import com.mjc.school.repository.filter.pagination.Pagination;
import com.mjc.school.repository.filter.sorting.SortOrder;
import com.mjc.school.repository.filter.sorting.Sorting;
import com.mjc.school.repository.filter.specification.EntitySearchSpecification;
import com.mjc.school.repository.filter.specification.SearchCriteria;
import com.mjc.school.repository.filter.specification.SearchFilterSpecificationBuilder;
import com.mjc.school.repository.filter.specification.SearchOperation;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@AutoConfigureTestDatabase
@ContextConfiguration(classes = { JpaRepositoryConfig.class })
@ExtendWith(SpringExtension.class)
@DataJpaTest
class RepositoryTest {

    static final String AUTHOR_NAME = "TestAuthorName";
    static final String TAG_NAME = "TestTagName";
    static final String NEWS_TITLE = "TestNewsTitle";
    static final String NEWS_CONTENT = "TestNewsContent";
    static final String COMMENT_CONTENT = "TestCommentContent";
    @Autowired
    BaseRepository<Tag, Long> tagRepository;
    @Autowired
    BaseRepository<Comment, Long> commentRepository;
    @Autowired
    BaseRepository<Author, Long> authorRepository;
    @Autowired
    BaseRepository<News, Long> newsRepository;
    Tag tag;
    Tag expectedTag;
    Comment comment;
    Comment expectedComment;
    Author author;
    Author expectedAuthor;
    News news;
    News expectedNews;


    @BeforeEach
    void init() {
        author = Author.builder()
                .name(AUTHOR_NAME)
                .build();
        tag = Tag.builder()
                .name(TAG_NAME)
                .build();
        news = News.builder()
                .title(NEWS_TITLE)
                .content(NEWS_CONTENT)
                .author(author)
                .tags(List.of(tag))
                .build();
        comment = Comment.builder()
                .content(COMMENT_CONTENT)
                .build();
        expectedTag = tagRepository.save(tag);
        expectedAuthor = authorRepository.save(author);
        expectedNews = newsRepository.save(news);
        expectedComment = commentRepository.save(comment);
    }

    @Test
    void shouldReadEntityWithCriteria() {
        Page<News> pages;
        newsRepository.save(News.builder().title("Test").build());

        EntitySearchSpecification searchSpecification = new EntitySearchSpecification();
        assertNull(SearchOperation.getDataOption(""));
        assertNull(SearchOperation.getSimpleOperation(""));

        searchSpecification.setPagination(new Pagination(1, 5));
        searchSpecification.setSortingList(List.of(new Sorting("title", SortOrder.ASC)));
        pages = newsRepository.readAll(searchSpecification);

        assertThat(pages.currentPage()).isEqualTo(1);
        assertThat(pages.pageCount()).isEqualTo(1);
        assertThat(pages.entities()).hasSize(2);
        assertThat(pages.entities().get(0).getTitle()).isEqualTo("Test");

        searchSpecification.setPagination(new Pagination(1, 2));
        searchSpecification.setSortingList(List.of(new Sorting("title", SortOrder.DESC)));
        pages = newsRepository.readAll(searchSpecification);
        assertThat(pages.currentPage()).isEqualTo(1);
        assertThat(pages.pageCount()).isEqualTo(1);
        assertThat(pages.entities().get(0).getTitle()).isEqualTo(NEWS_TITLE);

        shouldFindContainEntity(searchSpecification);
        shouldFindNotContainEntity(searchSpecification);
        shouldFindEqualsAndNotEqualsEntity(searchSpecification);
        shouldFindNotEqualsOrEqualsEntity(searchSpecification);
        nullCheckSpecification(searchSpecification);
    }

    void shouldFindContainEntity(EntitySearchSpecification entitySearchSpecification) {
        entitySearchSpecification.setSpecification(new SearchFilterSpecificationBuilder<>("")
                .with(new SearchCriteria("title", "cn", "T"))
                .build());
        Page<News> pages = newsRepository.readAll(entitySearchSpecification);
        assertThat(pages.entities()).hasSize(2);
    }

    void shouldFindNotContainEntity(EntitySearchSpecification entitySearchSpecification) {
        entitySearchSpecification.setSpecification(new SearchFilterSpecificationBuilder<>("all")
                .with(new SearchCriteria("title", "nc", "T"))
                .build());
        Page<News> pages = newsRepository.readAll(entitySearchSpecification);
        assertThat(pages.entities()).isEmpty();
    }

    void shouldFindEqualsAndNotEqualsEntity(EntitySearchSpecification entitySearchSpecification) {
        entitySearchSpecification.setSpecification(new SearchFilterSpecificationBuilder<>("all")
                .with(List.of(
                        new SearchCriteria("title", "eq", "Title"),
                        new SearchCriteria("title", "ne", "Author")
                ))
                .build());
        Page<News> pages = newsRepository.readAll(entitySearchSpecification);
        assertThat(pages.entities()).isEmpty();
    }

    void nullCheckSpecification(EntitySearchSpecification entitySearchSpecification) {
        entitySearchSpecification.setSpecification(new SearchFilterSpecificationBuilder<>("")
                .with(Collections.emptyList())
                .build());
        assertNull(entitySearchSpecification.getSpecification());
    }

    void shouldFindNotEqualsOrEqualsEntity(EntitySearchSpecification entitySearchSpecification) {
        entitySearchSpecification.setSpecification(new SearchFilterSpecificationBuilder<>("any")
                .with(List.of(
                        new SearchCriteria("title", "ne", "Title"),
                        new SearchCriteria("author", "eq", "Author"),
                        new SearchCriteria("comments", "eq", "C"),
                        new SearchCriteria("tagsId", "eq", "1"),
                        new SearchCriteria("tags", "ne", "TAG")
                ))
                .build());
        Page<News> pages = newsRepository.readAll(entitySearchSpecification);
        assertThat(pages.entities()).isEmpty();
    }

    @Test
    void shouldFindEntityById() {
        Optional<News> news = newsRepository.findById(expectedNews.getId());
        assertThat(news).isPresent();
    }

    @Test
    void shouldReadEntityByNewsId() {
        List<Author> authors = authorRepository.readByNewsId(news.getId());
        assertThat(authors.get(0).getId()).isEqualTo(author.getId());
    }

}
