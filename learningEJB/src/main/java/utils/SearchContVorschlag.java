package utils;

public class SearchContVorschlag extends SearchContainer {

  public SearchContVorschlag(String content, SearchItems searchItem, long id) {
    super(content, searchItem, id);
  }

  @Override
  public boolean equals(Object anObject) {
		String stObject = ((SearchContainer) anObject).getContent();

		return content.equalsIgnoreCase(stObject);
	}

	@Override
  public int hashCode() {
    return content.hashCode();
  }

}
