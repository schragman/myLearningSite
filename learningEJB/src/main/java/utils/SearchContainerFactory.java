package utils;

public interface SearchContainerFactory {

  public SearchContainer getSearchContainer(String content, SearchItems searchItem, long id);

}
