package controller;

import beans.UserBean;
import secEntities.Users;
import util.Sites;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named
public class MenuForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserBean userBean;
	private SelectedPage selectedPage;
	private boolean mCategory;
	private boolean mTheme;
	private boolean mSearch;
	private boolean mWalkthrough;
	private boolean mList;
	private boolean mEdit;
	private boolean mEntry;
	private boolean mNavigation;

	public void setSelectedPage(SelectedPage selectedPage) {
		this.selectedPage = selectedPage;

		switch (this.selectedPage) {
		case HOME:
			mCategory = true;
			mTheme = true;
			mSearch = false;
			mWalkthrough = false;
			mList = false;
			mEdit = false;
			mEntry = false;
			mNavigation = false;
			break;
		case MAINENTRY:
			mCategory = false;
			mTheme = false;
			mSearch = false;
			mWalkthrough = false;
			mList = true;
			mEdit = true;
			mEntry = false;
			mNavigation = true;
			break;
		case UEBERSICHT:
			mCategory = false;
			mTheme = false;
			mSearch = true;
			mWalkthrough = true;
			mList = false;
			mEdit = false;
			mEntry = true;
			mNavigation = false;
			break;
		}
	}

	public boolean ismCategory() {
		return mCategory;
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

	public boolean ismNavigation() {return mNavigation;}

	public String getCurrentUser() {return userBean.getUsername();}


}
