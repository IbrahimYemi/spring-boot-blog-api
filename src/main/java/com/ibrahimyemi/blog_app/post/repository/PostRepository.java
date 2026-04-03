package com.ibrahimyemi.blog_app.post.repository;
import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ibrahimyemi.blog_app.post.entity.Post;
import com.ibrahimyemi.blog_app.user.entity.User;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findAllByAuthor(User user, Pageable pageable);

    Page<Post> findAllByPublishedTrue(Pageable pageable);
}
