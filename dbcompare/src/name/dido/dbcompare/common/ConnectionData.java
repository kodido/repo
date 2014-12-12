package name.dido.dbcompare.common;

public class ConnectionData {
	
	private String driver;
	private String url;
	private String user;
	private String passwd;		
	
	public ConnectionData(String driver, String url, String user,
			String passwd) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.passwd = passwd;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPasswd() {
		return passwd;
	}
}
