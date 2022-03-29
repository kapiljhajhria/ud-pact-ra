package com.jhajhria.CoursesApplication.repository;

import com.jhajhria.CoursesApplication.controller.AllCourseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<AllCourseData, String>{

}