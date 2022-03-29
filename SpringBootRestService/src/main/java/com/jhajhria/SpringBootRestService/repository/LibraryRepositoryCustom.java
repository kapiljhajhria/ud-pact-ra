package com.jhajhria.SpringBootRestService.repository;

import com.jhajhria.SpringBootRestService.controller.Library;

import java.util.List;

public interface LibraryRepositoryCustom {
	
	List<Library> findAllByAuthor(String authorName);

	Library findByName(String bookName);

}