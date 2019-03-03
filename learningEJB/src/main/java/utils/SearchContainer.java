package utils;

import java.io.Serializable;

public class SearchContainer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String content;
	private SearchItems searchItem;

	
	public SearchContainer(String content, SearchItems searchItem, long id) {
		this.content=content;
		this.searchItem=searchItem;
		this.id=id;
	}
	
	public int hashCode() {
		return content.hashCode();
	}
	
	public boolean equals(Object anObject) {
		String stObject = ((SearchContainer) anObject).getContent();
		
		return content.equalsIgnoreCase(stObject);
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public SearchItems getSearchItem() {
		return searchItem;
	}
	
	public void setSearchItem(SearchItems searchItem) {
		this.searchItem = searchItem;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
}
