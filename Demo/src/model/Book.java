package model;

public class Book {

		private int bookNo;
		private String bookName;
		private String bookType;
		private String bookCategory;
		
		public int getBookNo() {
			return bookNo;
		}
		
		public void setBookNo(int bookNo){
			this.bookNo = bookNo;
		}
		
		public String getBookName() {
			return bookName;
		}

		public void setBookName(String bookName) {
			this.bookName = bookName;
		}		
		
		public String getBookType() {
			return bookType;
		}

		public void setBookType(String bookType) {
			this.bookType = bookType;
		}
		
		public String getBookCategory() {
			return bookCategory;
		}
		
		public void setBookCategory(String bookCategory){
			this.bookCategory = bookCategory;
		}
}
