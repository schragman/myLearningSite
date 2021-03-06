package utils;

import java.io.Serializable;

public abstract class SearchContainer implements Serializable{
	
	protected static final long serialVersionUID = 1L;
	protected long id;
	protected String content;
	protected SearchItems searchItem;

	public SearchContainer(String content, SearchItems searchItem, long id) {
		this.content=content;
		this.searchItem=searchItem;
		this.id=id;
	}
	


	/*public int hashCode() {
		return content.hashCode();
	}*/

	public abstract int hashCode();

	public abstract boolean equals(Object anObject) ;/*{
		String stObject = ((SearchContainer) anObject).getContent();

		return content.equalsIgnoreCase(stObject);
	}*/



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
