package com.jhajhria.SpringBootRestService.repository;

import com.jhajhria.SpringBootRestService.controller.Library;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LibraryRepositoryImpl implements LibraryRepositoryCustom{
	
	@Autowired
	LibraryRepository repository;

	@Override
	public List<Library> findAllByAuthor(String authorName) {
		List<Library>bookswithAuthor = new ArrayList<Library>();
		// TODO Auto-generated method stub
		List<Library>books =repository.findAll();
		for(Library item : books)
//			{
	if(item.getAuthor().equalsIgnoreCase(authorName))
	{
		bookswithAuthor.add(item);
	}
//			}
		
		return bookswithAuthor;
	}
	
	@Override
	public Library findByName(String bookName) {
		List<Library>bookswithAuthor = new ArrayList<Library>();
		// TODO Auto-generated method stub
		List<Library>books =repository.findAll();
		for(Library item : books)
//			{
	if(item.getBook_name().equalsIgnoreCase(bookName))
	{
		return item;
	}
//			}
		return null;
		
		
	}
	

}