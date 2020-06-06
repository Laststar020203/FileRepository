package com.laststar.fileRepository;
import com.laststar.fileRepository.Paging;

public class Test {

	public static void main(String[] args) {
		
		Paging paging = new Paging(112);
		System.out.println(paging.getMaxPage());
		System.out.println("PAGE : " + paging.getPage());
		System.out.println("START PAGE : " + paging.getStartPage());
		System.out.println("END PAGE : " + paging.getEndPage());
		System.out.println("START ROW : " + paging.getStartRow());
		System.out.println("END ROW : " + paging.getEndRow());
		System.out.println("CAN NEXT : " + paging.isCanNext());
		System.out.println("CAN PREVIOUS : " + paging.isCanPrevious());
		System.out.println("UPDATEWARNING : " + paging.isUpdateWarnning());
		
		paging.next();
		System.out.println("=============================================");
		System.out.println("PAGE : " + paging.getPage());
		System.out.println("START PAGE : " + paging.getStartPage());
		System.out.println("END PAGE : " + paging.getEndPage());
		System.out.println("START ROW : " + paging.getStartRow());
		System.out.println("END ROW : " + paging.getEndRow());
		System.out.println("CAN NEXT : " + paging.isCanNext());
		System.out.println("CAN PREVIOUS : " + paging.isCanPrevious());
		System.out.println("UPDATEWARNING : " + paging.isUpdateWarnning());
		
		paging.setPage(5);
		System.out.println("=============================================");
		System.out.println("PAGE : " + paging.getPage());
		System.out.println("START PAGE : " + paging.getStartPage());
		System.out.println("END PAGE : " + paging.getEndPage());
		System.out.println("START ROW : " + paging.getStartRow());
		System.out.println("END ROW : " + paging.getEndRow());
		System.out.println("CAN NEXT : " + paging.isCanNext());
		System.out.println("CAN PREVIOUS : " + paging.isCanPrevious());
		System.out.println("UPDATEWARNING : " + paging.isUpdateWarnning());
		

	}
}
