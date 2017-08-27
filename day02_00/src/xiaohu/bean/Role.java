package xiaohu.bean;



public class Role {
	private int rownum;
	

	private long id;
	private String name;
	private String remark;
	private boolean checked=false;
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Role){
			Role role=(Role)o;
			if(id==role.getId()&&name.equals(role.getName())&&remark.equals(role.getRemark())){
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode(){
		long temp=id*3+Long.valueOf(name)*14+Long.valueOf(remark)*9+87;
		return (int)temp;
	}
	
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Role(long id, String name, String remark) {
		super();
		this.id = id;
		this.name = name;
		this.remark = remark;
	}
	
	public Role(long id,int rownum, String name, String remark) {
		super();
		this.rownum = rownum;
		this.id = id;
		this.name = name;
		this.remark = remark;
	}
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	
}
