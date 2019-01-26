package com.jitin.hibernatebasics.dto;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/*
 * In order to work with hibernate you have to mark you model classes with @Entity.
 * If you're not providing any name to your entity class hibernate will automatically
 * create table into the database with same name as your entity. Eg. Teacher
 */
@Entity

/*
 * By providing the name to your entity, you are providing the new name to the
 * entity itself from Teacher to TEACHER_INFO. In this case hibernate will create the
 * table into database with name that you have provided. Eg. TEACHER_INFO
 */
// @Entity(name = "TEACHER_INFO")

/*
 * By using the @Table annotation you are controlling the table name. You are
 * not providing the new name to the entity. In this case your entity name still
 * will be same Eg. Teacher & hibernate will create table into database with the
 * name you have provided. Eg. TEACHER_INFO
 */
@Table(name = "TEACHER_INFO")
public class Teacher {
	/*
	 * We use this @Id annottaion to create primary keys into your tables.
	 */
	@Id
	/*
	 * We can customize the column names into database by using this @Column
	 * annotation.If you don't use @Column annotation hibernate will create column
	 * into database with the same name as your field name. Eg. Id, but if you want
	 * to change the column name into the database table, then you can for @Column
	 * annotation. Eg. Column name will be TEACHER_ID.
	 */
	@Column(name = "TEACHER_ID")
	/*
	 * There are 2 types of keys : (1). Surrogate key : A surrogate key is a system
	 * generated (could be GUID, sequence, etc.) value with no business meaning that
	 * is used to uniquely identify a record in a table. (2). Natural Keys : A
	 * natural key is a column or set of columns that already exist in the table
	 * (e.g. they are attributes of the entity within the data model) and uniquely
	 * identify a record in the table. Eg : EMAIL, ROLL_NUMBER, SSN
	 */

	/*
	 * Here we have surrogate key. Hibernate will generate this for us. We don't
	 * need to pass this value, hibernate will take care of it.
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*
	 * If you want to create composite ids then you can use this @EmbeddedId. When
	 * you mark a field as @EmbeddedId in that case hibernate will treat combination
	 * of all the member variable of the object as a primary key. Eg. You have a
	 * separate class called Student(object) with 2 member variables, class &
	 * rollNumber. In a school, class & rollNumber will always be a unique
	 * combination so you can use this Student class as your primary key by marking
	 * id field as @EmbeddedId. This will look like : @EmbeddedId private Student
	 * id;
	 */
	// @EmbeddedId
	private Integer id;

	/*
	 * Also we can use these annotations on the top of the getter. If you don't
	 * annotate a field @Column, hibernate will automatically create column with the
	 * same name as field.
	 */

	@Column(name = "TEACHER_NAME")
	private String name;
	/*
	 * If you don't want to store time with date into database table, you can mark
	 * your field as @Temporal and provide the TemporalType.
	 */
	@Temporal(TemporalType.DATE)
	private Date dob;
	/*
	 * What @Basic does? It actually treated as a fields which should be persisted &
	 * apply the hibernate default but it's happening even without @Basic. So
	 * the @Basic is not required. We can use this if we want to configure few other
	 * things like fetch & optional.
	 */
	// @Basic

	/*
	 * If you want to store multiple values for a single teacher then you can
	 * use @ElementCollection. Suppose you want to store multiple addresses(List or
	 * Set of address) for a teacher then you have to mark addresses field
	 * as @ElementCollection & also you have to mark Address class as @Embeddable.
	 * If you do so hibernate will separately create a table to store all the
	 * addresses associated with a teacher and hibernate will automatically create a
	 * foreign key in that table & the value of this foreign key will always be the
	 * same as teacher id(Primary key in TEACHER_INFO table). Table name will be your
	 * entity name(TEACHER) appended with field name(addresses) by underscore(_)
	 * symbol. Eg. Teacher_addresses & the foreign key column name will be you entity
	 * name(Teacher) appended with the primary key column of your entity by a
	 * underscore symbol. Eg. Teacher_TEACHER_ID
	 */

	/*
	 * Here I used fetch type eager because I always want to pull addresses with
	 * teacher.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	/*
	 * @JoinTable is not mandatory to use but when you want to customize the name of
	 * your table and the name of your foreign key column, you have to use this.
	 */
	@JoinTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "TEACHER_ID"))
	private Set<Address> addresses;

	/*
	 * Here we are embedding our contact class. We'll get the phone & email column
	 * into same TEACHER_INFO table. We can use this same Contact class as many times
	 * we want. In our case we are using this Contact class twice in this Teacher
	 * class, one for officeContact & another one is for personalContact. In order
	 * to do that we have to override the attributes because we can't create same
	 * column twice in a single table therefore we have to specify the different
	 * name for each column even if they'll going to have the same type of data but
	 * data might be different for each column. Eg. one phone column have the office
	 * phone number and another one have the personal phone number so we can't have
	 * 2 same column name in a single table like PHONE_NUMBER & PHONE_NUMBER, thats
	 * why we have to specify different column name for each column like
	 * PHONE_NUMBER & OFFICE_PHONE_NUMBER or PHONE_NUMBER & PERSONAL_PHONE_NUMBER
	 */

	/*
	 * Since you have already marked Contact class as @Embeddable therefore its not
	 * mandatory to mark this property as @Embedded here. It'll work fine even if
	 * you don't mark this as @Embedded
	 */
	@Embedded
	/*
	 * Since we want to specify the different column name for both fields therefore
	 * we have @AttributeOverride inside @AttributeOverrides. If we only want to
	 * give different name to a single field than we can simply
	 * use @AttributeOverride like @AttributeOverride(name = "email", column
	 * = @Column(name = "OFFICE_EMAIL")) without using @AttributeOverrides({})
	 */
	@AttributeOverrides({ @AttributeOverride(name = "phone", column = @Column(name = "OFFICE_PHONE")),
			@AttributeOverride(name = "email", column = @Column(name = "OFFICE_EMAIL")) })
	private Contact officeContact;

	/*
	 * If you don't use @AttributeOverrides & @AttributeOverride then hibernate will
	 * create the column by default name which you have already specified in the
	 * Contact class. Since we have already provided the column name for
	 * officeContant, we don't need to do the same here but if you don't want column
	 * names as your field names into Contact class like phone & email or you want
	 * to change the column names for personalContact like PERSONAL_PHONE &
	 * PERSONAL_EMAIL then you can do the same thing here.
	 */
	@Embedded
	private Contact personalContact;

	/*
	 * If you want to store time only, you can use TemporalType.TIME.
	 */
	// @Temporal(TemporalType.TIME)
	private Date activeOn;

	/*
	 * If you want to generate a primary key for each row then you have to use a
	 * collection type which support indexing. HashSet doesn't support indexing so
	 * we can't use that. ArrayList supports indexing thats why we are using
	 * ArrayList here.
	 */
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name = "POST_ID") }, generator = "hilo-gen", type = @Type(type = "long"))
	/*
	 * Here I didn't provide any fetch type because I don't want to pull posts with
	 * teacher until & unless I call the getter to get the posts for that teacher.
	 */
	@ElementCollection
	@JoinTable(name = "POST", joinColumns = @JoinColumn(name = "TEACHER_ID"))
	private Collection<Post> posts;
	/*
	 * By default hibernate creates varchar(255) for strings but what if we want to
	 * store more than that. In order to do that we have to annotate our large
	 * object as @Lob. We have two types or large objects : 1-CLOB & 2-BLOB. By
	 * marking a field as @Lob we are telling hibernate to choose one these whether
	 * its a character lob or a byte stream.
	 */
	@Lob
	private String about;
	/*
	 * To establish the one to one mapping with department we have to mark this
	 * field with @OneToOne. Its a entity type where as addresses & posts are value
	 * type.
	 */
	@OneToOne
	/*
	 * To specify the name of the column which will be created into the TEACHER_INFO
	 * table. Its not mandatory to specify a name for this column. If we don't
	 * specify the name here hibernate will automatically create a name for this
	 * column & that name will be EntityName_FieldName. Eg. department_departmentId
	 */
	@JoinColumn(name = "DEPT_ID")
	private Department department;

	@OneToMany
	/*
	 * If we use @JoinColumn here hibernate will create column PHONE_ID into the
	 * table PHONE, here we're not doing so therefore hibernate will create a
	 * separate table like TEACHER_INFO_PHONE with 2 columns TEACHER_INFO_TEACHER_ID &
	 * phones_phoneId.
	 */

	// @JoinColumn(name = "PHONE_ID")

	/*
	 * Here we are customizing our table name and column names by using @JoinTable
	 * annotation. We can do this even for one to one relationship but it doesn't
	 * make more sense to do this for one to one.
	 */
	@JoinTable(name = "TEACHER_PHONE", joinColumns = @JoinColumn(name = "TEACHER_ID"), inverseJoinColumns = @JoinColumn(name = "PHONE_ID"))
	private Collection<Phone> phones;

	/*
	 * Another way to establish one to many & many to one relationship. In the case
	 * of phone whatever we have done, hibernate will create a separate table called
	 * TEACHER_PHONE having only columns TEACHER_ID & PHONE_ID. But in case of vehicle we
	 * don't want to create a separate table to store teacher id & vehicle id instead
	 * of we can a have 3rd column into Vehicle table to store the TEACHER_ID. In order to
	 * achieve this we can provide mapped by into @OneToMany annotation. This mapped
	 * by value should be same as you have property in Vehicle class for Teacher.
	 */
	@OneToMany(mappedBy = "teacher")
	/*
	 * You can use List<> rather than Collection.
	 */
	private Collection<Vehicle> vehicle;
	
	@ManyToMany
	private Collection<Student> students;
	
	/*
	 * If you don't want to store any of the member variable of entity class into
	 * database table then you need to mark it as @Transient
	 */
	@Transient
	private String favoriteQuote;

	public Teacher() {

	}

	public Teacher(String name, Date dob, Set<Address> addresses, Date activeOn, String about) {
		this.name = name;
		this.dob = dob;
		this.addresses = addresses;
		this.activeOn = activeOn;
		this.about = about;
	}

	public Teacher(String name, Date dob, Set<Address> addresses, Date activeOn, String about, String favoriteQuote) {
		this.name = name;
		this.dob = dob;
		this.addresses = addresses;
		this.activeOn = activeOn;
		this.about = about;
		this.favoriteQuote = favoriteQuote;
	}

	// @Id
	// @Column(name="TEACHER_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// @Column(name="TEACHER_NAME")
	public String getName() {
		return name;
		/*
		 * For testing purpose : Hibernate will store the value name with appended
		 * string because when we put annotations on getters, hibernate picks the value
		 * from getters. NOTE : We have to put all annotations on getters. We can't have
		 * one annotation on property and one on getter. If you do so it won't give any
		 * error but capture the value from property not from getter.
		 */
		// return name + " value from getter";
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getActiveOn() {
		return activeOn;
	}

	public void setActiveOn(Date activeOn) {
		this.activeOn = activeOn;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Contact getOfficeContact() {
		return officeContact;
	}

	public void setOfficeContact(Contact officeContact) {
		this.officeContact = officeContact;
	}

	public Contact getPersonalContact() {
		return personalContact;
	}

	public void setPersonalContact(Contact personalContact) {
		this.personalContact = personalContact;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Collection<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Collection<Phone> phones) {
		this.phones = phones;
	}

	public Collection<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(Collection<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}

	public String getFavoriteQuote() {
		return favoriteQuote;
	}

	public void setFavoriteQuote(String favoriteQuote) {
		this.favoriteQuote = favoriteQuote;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", dob=" + dob + ", addresses=" + addresses + ", officeContact="
				+ officeContact + ", personalContact=" + personalContact + ", activeOn=" + activeOn + ", posts=" + posts
				+ ", about=" + about + ", department=" + department + ", phones=" + phones + ", vehicle=" + vehicle
				+ ", students=" + students + ", favoriteQuote=" + favoriteQuote + "]";
	}
	
}
