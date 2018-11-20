package com.student.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.student.model.Student;
import com.student.service.StudentService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin
@RestController
public class StudentController {

	@Autowired
	StudentService studentService; // Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All
	// Students--------------------------------------------------------

	@RequestMapping(value = "/students/", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> listAllStudents(HttpServletRequest request) {
		List<Student> Students = studentService.findAllStudents();

		if (Students.isEmpty()) {
			return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);
		} else if (request.getHeader("Authorization").isEmpty()) {
			return new ResponseEntity<List<Student>>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<List<Student>>(Students, HttpStatus.OK);
	}

	/*
	 * //-------------------Retrieve Single
	 * Student--------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/student/{id}", method = RequestMethod.GET, produces
	 * = MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<Student>
	 * getStudentById(@PathVariable("id") int id) {
	 * System.out.println("Fetching Student with id " + id); Student Student =
	 * studentService.findById(id); if (Student == null) {
	 * System.out.println("Student with id " + id + " not found"); return new
	 * ResponseEntity<Student>(HttpStatus.NOT_FOUND); } return new
	 * ResponseEntity<Student>(Student, HttpStatus.OK); }
	 */

	// -------------------Retrieve Multiple Students By
	// ID/Name/SECTION--------------------------------------------------------

	@RequestMapping(value = "/student/search/{value}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Student>> getStudentsBySearchValue(@PathVariable("value") String value) {
		System.out.println("Fetching Student with id/name/section " + value);
		List<Student> Student = studentService.findBySearchValue(value);
		if (Student == null) {
			System.out.println("Student with id/name/section " + value + " not found");
			return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Student>>(Student, HttpStatus.OK);
	}

	// -------------------Create a
	// Student--------------------------------------------------------

	@RequestMapping(value = "/student/", method = RequestMethod.POST)
	public ResponseEntity<Void> createStudent(@RequestBody Student student, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Student " + student.getFname());

		// if (studentService.isStudentExist(student)) {
		// System.out.println("A Student with name " + student.getFname() + " already
		// exist");
		// return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		// }

		studentService.saveStudent(student);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Student
	// --------------------------------------------------------

	@RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student Student) {
		System.out.println("Updating Student " + id);

		Student currentStudent = studentService.findById(id);

		if (currentStudent == null) {
			System.out.println("Student with id " + id + " not found");
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}

		currentStudent.setFname(Student.getFname());
		currentStudent.setLname(Student.getLname());
		currentStudent.setSection(Student.getSection());

		studentService.updateStudent(currentStudent);
		return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
	}

	// ------------------- Delete a Student
	// --------------------------------------------------------

	@RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") int id) {
		System.out.println("Fetching & Deleting Student with id " + id);

		Student Student = studentService.findById(id);
		if (Student == null) {
			System.out.println("Unable to delete. Student with id " + id + " not found");
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}

		studentService.deleteStudentById(id);
		return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Students
	// --------------------------------------------------------

	@RequestMapping(value = "/student/", method = RequestMethod.DELETE)
	public ResponseEntity<Student> deleteAllStudents() {
		System.out.println("Deleting All Students");

		studentService.deleteAllStudents();
		return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestBody Map<String, String> account, HttpServletResponse response) {

		if (studentService.isStudentExist(account) == null) {
			System.err.println("This user account does not exist" + " " + studentService.isStudentExist(account));
		} else {
			String jwt = createJWT(account.get("uname"));
			response.addHeader("Authorization", "Bearer " + jwt);
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(@RequestBody Map<String, String> account, HttpServletResponse response) {

		response.setHeader("Authorization", null);

	}

	// from : https://stormpath.com/blog/jwt-java-create-verify
	private String createJWT(String uname) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		JwtBuilder builder = Jwts.builder().setId(uname).setIssuedAt(now).setSubject("This is a subject")
				.setIssuer("This is an issuer").signWith(signatureAlgorithm, "this is a secret key");

		return builder.compact();
	}
}
