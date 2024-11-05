package com.stagora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.stagora.dao.others.StorageRepository;
import com.stagora.entities.others.ImageData;

public class StorageService {
	
	@Autowired
	private StorageRepository storageRepository;
	
//	public String uplaodImage(MultipartFile file){
//		//storageRepository.save(ImageData);
//	}
}
