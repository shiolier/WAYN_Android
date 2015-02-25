package jp.gr.java_conf.shiolier.wayn.entity;

public class AppInfo {
	public static final String KEY_VERSION_CODE = "version_code";
	public static final String KEY_VERSION_NAME = "version_name";

	private int versionCode;
	private String versionName;

	public AppInfo() {
	}

	public AppInfo(int versionCode, String versionName) {
		this.versionCode = versionCode;
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
}
