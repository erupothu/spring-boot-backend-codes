package com.bestow.cms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bestow.cms.model.BlogModel;

@Repository
public interface BlogRepository extends JpaRepository<BlogModel, Integer>{

}
