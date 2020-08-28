package controller;

import beans.ThemenSteuerungRemote;
import beans.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Sites;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class ConfigurationForm implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(ConfigurationForm.class);

	private static final long serialVersionUID = 1L;

	@EJB
	private UserBean userBean;

	private int nbDescrLines, nbSampleLines;

	@PostConstruct
	public void init() {
		nbDescrLines = userBean.getNbLines(UserBean.ConfigType.DESCR_LINES);
		nbSampleLines = userBean.getNbLines(UserBean.ConfigType.SAMPLE_LINES);
	}

	public int getNbDescrLines() {
		return nbDescrLines;
	}

	public void setNbDescrLines(int nbDescrLines) {
		this.nbDescrLines = nbDescrLines;
	}

	public int getNbSampleLines() {
		return nbSampleLines;
	}

	public void setNbSampleLines(int nbSampleLines) {
		this.nbSampleLines = nbSampleLines;
	}

	public void doOk() {
		userBean.changeNbLines(this.nbSampleLines, UserBean.ConfigType.SAMPLE_LINES);
		userBean.changeNbLines(this.nbDescrLines, UserBean.ConfigType.DESCR_LINES);
	}
}
