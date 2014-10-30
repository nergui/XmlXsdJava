package doppiogroup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "projects", catalog = "orderlist", 
uniqueConstraints = @UniqueConstraint(columnNames = "TITLE"))
public class Project implements java.io.Serializable{
	

	private Integer projectId;
	
	private String title;
	private String semester;
	
	private Student student;
	
	
	public Project(String title, String semester){
		this.title = title;
		this.semester = semester;
	}
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PROJECT_ID", unique = true, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "TITLE", nullable = false, length = 100, unique = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "SEMESTER", nullable = false, length = 100)
	public String getSemester() {
		return semester;
	}

	
	
	public void setSemester(String semester) {
		this.semester = semester;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	private static final long serialVersionUID = 1L;

	

}
