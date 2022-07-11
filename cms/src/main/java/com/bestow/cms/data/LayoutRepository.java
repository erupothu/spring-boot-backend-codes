package com.bestow.cms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bestow.cms.model.LayoutModel;

@Repository
public interface LayoutRepository extends JpaRepository<LayoutModel, Integer>{

}
