package com.bestow.cms.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bestow.cms.model.AssetsModel;

public interface AssetsRepository extends JpaRepository<AssetsModel, Integer> {

}
