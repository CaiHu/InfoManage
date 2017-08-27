package xiaohu.bean;

import java.util.List;

public class Page{
	private int pageNumber=3;
	private int currentPage;
	private int totalPage;
	private int totalRow;

	
	public Page() {
		
	}
	public Page(int pageNumber, int currentPage, int totalPage, int totalRow) {
		super();
		this.pageNumber = pageNumber;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

}
