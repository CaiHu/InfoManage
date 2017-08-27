package xiaohu.bean;

public class User {
	private long id;
	private int rownum;
	private String name;
	private String password;
	private String phone;
	
	public User() {
	}
	
	
	
	public User(int rownum, String name, String password, String phone) {
		super();
		this.rownum = rownum;
		this.name = name;
		this.password = password;
		this.phone = phone;
	}

	

	public User(long id, String name, String password, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
	}



	public User(long id, int rownum, String name, String password, String phone) {
		super();
		this.id = id;
		this.rownum = rownum;
		this.name = name;
		this.password = password;
		this.phone = phone;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
