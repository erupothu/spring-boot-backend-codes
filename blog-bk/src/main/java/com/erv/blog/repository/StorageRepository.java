package com.erv.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erv.blog.entity.StorageEntity;

@ResponseBody
public interface StorageRepository extends JpaRepository<StorageEntity, Integer>{

}
