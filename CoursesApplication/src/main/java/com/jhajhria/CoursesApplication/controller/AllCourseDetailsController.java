package com.jhajhria.CoursesApplication.controller;

import com.jhajhria.CoursesApplication.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class AllCourseDetailsController {


@Autowired
CoursesRepository repository;



@CrossOrigin
	@GetMapping("/allCourseDetails")
	public List<AllCourseData> GetCourses()
	{

	return repository.findAll();
		
		
	}


@CrossOrigin
@RequestMapping("/getCourseByName/{name}")
public AllCourseData getBookById(@PathVariable(value="name")String name)
{
	try {
		AllCourseData lib =repository.findById(name).get();
	return lib;
	}
	catch(Exception e)
	{
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
}
	
	
	
	
	
}