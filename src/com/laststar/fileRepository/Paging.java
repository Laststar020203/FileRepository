package com.laststar.fileRepository;

public class Paging {
	
	private final int MAX_PAGE_BLOCK = 10;
	private final int MAX_ROW_BLOCK = 10;
	private int totalRowCount;
	private int maxPage;
	private int page;
	private int startPage;
	private int endPage;
	
	
	private boolean canNext;
	private boolean canPrevious;
	private boolean isUpdateWarnning;
	//DB row 증가로 endPage의 실질적 수치 변경 위험성 감지 해당 값이 true일경우 반드시 totalRowCount를 재입력할 것
	
	public Paging(int totalRowCount) {
		this.totalRowCount = totalRowCount;
		this.page = 1;
		this.maxPage = (int)Math.ceil((double)totalRowCount/MAX_ROW_BLOCK);
		paging();
	}
	
	
	public int getMaxPage() {
		return maxPage;
	}


	private void paging() {
		int i = (int)Math.ceil((double)page/MAX_PAGE_BLOCK);
		startPage = 1 + (MAX_PAGE_BLOCK * (i - 1));
		endPage = 10 + (MAX_PAGE_BLOCK * (i - 1));
		
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		canPrevious = startPage - 1 <= 0 ? false : true;
		canNext = startPage + MAX_PAGE_BLOCK > maxPage ? false : true;
		isUpdateWarnning = !canNext ? true : false;
		
	}
	
	public void setPage(int page) {
		this.page = page;
		if(page > endPage || page < startPage)
			paging();
			
	}
	
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
		paging();
	}
	
	public void next() {
		if(!canNext) return;
		this.page = endPage + 1;
		paging();
	}
	
	public void previous() {
		if(!canPrevious) return;
		this.page = startPage - 1;
		paging();
	}
	
	public int getPage() {
		return page;
	}


	public int getStartPage() {
		return startPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public int getStartRow() {
		return 1 + (MAX_ROW_BLOCK * (page - 1));
	}

	public int getEndRow() {
		return 10 + (MAX_ROW_BLOCK * (page - 1));
	}

	public boolean isCanNext() {
		return canNext;
	}

	public boolean isCanPrevious() {
		return canPrevious;
	}

	public boolean isUpdateWarnning() {
		return isUpdateWarnning;
	}
	
	

	
}
