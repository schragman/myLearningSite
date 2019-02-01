package controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class MenuForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private SelectedPage selectedPage;
	private boolean mTheme;
	private boolean mSearch;
	private boolean mWalkthrough;
	private boolean mList;
	private boolean mEdit;
	private boolean mEntry;

	public void setSelectedPage(SelectedPage selectedPage) {
		this.selectedPage = selectedPage;

		switch (this.selectedPage) {
		case HOME:
			mTheme = true;
			mSearch = false;
			mWalkthrough = false;
			mList = false;
			mEdit = false;
			mEntry = false;
			break;
		case MAINENTRY:
			mTheme = false;
			mSearch = false;
			mWalkthrough = false;
			mList = true;
			mEdit = true;
			mEntry = false;
			break;
		case UEBERSICHT:
			mTheme = false;
			mSearch = true;
			mWalkthrough = true;
			mList = false;
			mEdit = false;
			mEntry = true;
			break;
		}
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

	public boolean ismEntry() {
		return mEntry;
	}

}
