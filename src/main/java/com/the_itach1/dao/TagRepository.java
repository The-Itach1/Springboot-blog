package com.the_itach1.dao;

import com.the_itach1.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    @Query("select tag from Tag tag")
    List<Tag> findTop(Pageable pageable);
}
