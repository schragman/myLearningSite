package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class MenuForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private SelectedPage selectedPage;
	private boolean mTheme;
	private boolean mSearch;
	private boolean mWalkthrough;
	private boolean mList;
	private boolean mEdit;

	public void setSelectedPage(SelectedPage selectedPage) {
		this.selectedPage = selectedPage;
		
		switch (this.selectedPage) {
			case SelectedPage.Home:
				
	}

	public boolean ismTheme() {
		return mTheme;
	}

	public boolean ismSearch() {
		return mSearch;
	}

	public boolean ismWalkthrough() {
		return mWalkthrough;
	}

	public boolean ismList() {
		return mList;
	}

	public boolean ismEdit() {
		return mEdit;
	}

}
