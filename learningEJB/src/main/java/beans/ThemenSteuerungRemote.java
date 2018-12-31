package beans;

import javax.ejb.Remote;

@Remote
public interface ThemenSteuerungRemote {

	public void generateNew(String themeN, String themeD);

}
