package Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUitls {
	private static final int CONNECT_TIMEOUT = 50000;
	private static final int READ_TIMEOUT = 50000;

	/**
	 * Get请求返回不指定编码的字符串
	 * 
	 * @param url
	 * @return
	 */
	public static String doGetToString(String url) {
		return new String(doGet(url));
	}

	/**
	 * Get请求返回指定编码的字符串
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String doGetToString(String url, String charset) {
		try {
			return new String(doGet(url), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Post请求返回不指定编码的字符串
	 * 
	 * @param url
	 * @return
	 */
	public static String doPostToString(String url) {
		return new String(doPost(url));
	}

	/**
	 * Post请求返回指定编码的字符串
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String doPostToString(String url, String charset) {
		try {
			return new String(doPost(url), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * GET方式请求网络,返回byte[]数组
	 * 
	 * @param url
	 * @return
	 */
	public static byte[] doGet(String url) {
		HttpURLConnection conn = null;
		try {
			URL mUrl = new URL(url);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				// 获取字节数组
				return getBytesFromInputStream(is);
			} else {
				System.out.println("网络连接失败：" + code);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * Post方式请求网络,返回byte[]数组
	 * 
	 * @param url
	 * @return
	 */
	public static byte[] doPost(String url) {
		String[] split = url.split("\\?");
		String urlStr = split[0];
		String data = split[1];
		HttpURLConnection conn = null;
		try {
			URL mUrl = new URL(urlStr);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setDoOutput(true);

			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();

			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				// 获取字节数组
				return getBytesFromInputStream(is);
			} else {
				System.out.println("网络连接失败：" + code);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * InputStream转换成byte[]数组
	 * 
	 * @param is
	 * @throws IOException
	 */
	private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		byte[] buffer = new byte[8192];
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		is.close();
		return baos.toByteArray();
	}

}
