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
			case HOME:
				mTheme=true;
				mSearch=false;
				mWalkthrough=false;
				mList=false;
				mEdit=false;
				break;
			case MAINENTRY:
				mTheme=false;
				mSearch=false;
				mWalkthrough=false;
				mList=true;
				mEdit=true;
				break;
			case UEBERSICHT:
				mTheme=false;
				mSearch=true;
				mWalkthrough=true;
				mList=false;
				mEdit=false;
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

}
