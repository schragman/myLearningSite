package utils;

public class SearchContList extends SearchContainer {

  public SearchContList(String content, SearchItems searchItem, long id) {
    super(content, searchItem, id);
  }

  @Override
  public boolean equals(Object anObject) {
		long idObject = ((SearchContainer) anObject).getId();

		return id == idObject;
	}

	@Override
  public int hashCode(){
    return (int) id;
  }

}
