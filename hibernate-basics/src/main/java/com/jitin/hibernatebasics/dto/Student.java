package com.jitin.hibernatebasics.dto;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Student {
	@Id
	@GeneratedValue
	private Integer studentId;
	private String name;

	/*
	 * Trying to achieve many to many relationship. Here are using mappedBy =
	 * "students" because we have already mapped Student into Teacher. When we mark
	 * any field as @ManyToMany it means that we are doing mapping or establishing a
	 * relationship. Since we have already do this in our Teacher class therefore we
	 * need to specify here that we have already done with the mapping somewhere
	 * else & by annotating your member variable with @ManyToMany(mappedBy =
	 * "students") is the way to specify.If you don't do this hibernate will create
	 * 2 separate mapping tables & if yo do so hibernate will create only one table
	 * like "teacher_info_student" with 2 columns teachers_TEACHER_ID &
	 * students_studentId.
	 */

	/*
	 * Note that the value of mappedBy should be same as the member variable name of
	 * your mapped class in your mapper class. Eg. In our case "students"(value of
	 * mappedBy) is the name of the member variable for Student(mapped class) in our
	 * Teacher(mapper class). private Collection<Student> students.
	 */
	@ManyToMany(mappedBy = "students")
	private Collection<Teacher> teachers;

	public Student() {
	}

	public Student(String name) {
		super();
		this.name = name;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Collection<Teacher> teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + ", teachers=" + teachers + "]";
	}

}
