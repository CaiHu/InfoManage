package xiaohu.bean;

public class Permission {
	private long id;
	private int rownum;
	private String userName;
	private String permissionName;
	private String url;
	private String remark;
	private long parentId;
	private boolean checked=false;
	
	
	
	
	public Permission(long id, int rownum, String permissionName, String url, String remark, long parentId) {
		super();
		this.id = id;
		this.rownum = rownum;
		this.permissionName = permissionName;
		this.url = url;
		this.remark = remark;
		this.parentId = parentId;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Permission){
			Permission permission=(Permission)o;
			if(id==permission.getId()&&permissionName.equals(permission.getPermissionName())){
				return true;
			}
		}
		return false;
	}
	@Override
	public int hashCode(){
		long temp=id*3+Long.valueOf(permissionName)*14;
		return (int)temp;
	}
	
	
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", userName=" + userName + ", permissionName=" + permissionName + ", url=" + url
				+ ", remark=" + remark + ", parentId=" + parentId + "]";
	}
	
	public Permission(long id, String userName, String permissionName, String url, String remark, long parentId) {
		super();
		this.id = id;
		this.userName = userName;
		this.permissionName = permissionName;
		this.url = url;
		this.remark = remark;
		this.parentId = parentId;
	}
	public Permission() {
		// TODO Auto-generated constructor stub
	}
	public Permission(long id, String permissionName, String url, String remark, long parentId) {
		super();
		this.id = id;
		this.permissionName = permissionName;
		this.url = url;
		this.remark = remark;
		this.parentId = parentId;
	}
	public Permission(long id, String permissionName) {
		super();
		this.id = id;
		this.permissionName = permissionName;
	}
	
}
