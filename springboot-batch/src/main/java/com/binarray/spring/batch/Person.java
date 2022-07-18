/**
 * 
 */
package com.binarray.spring.batch;

/**
 * @author Ashesh Krishna
 *
 */
public class Person {
	private String id;
	private String lastName;
	private String firstName;

	public Person() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Person(String id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "id: " + id +  ", firstName: " + firstName + ", lastName: " + lastName;
	}
	
}
