package beans;

import javax.annotation.PostConstruct;

public class AusgabeTest {
	private String writeTest;

	@PostConstruct
	public void init() {
		writeTest = "Test von der JAR-Bean";
	}

	public String getWriteTest() {
		return writeTest;
	}

	public void setWriteTest(String writeTest) {
		this.writeTest = writeTest;
	}
}
