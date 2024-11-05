package com.stagora.dao.others;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stagora.entities.others.ImageData;

public interface StorageRepository extends JpaRepository<ImageData, Long>{
	Optional<ImageData> findByName(String fileName);
}
