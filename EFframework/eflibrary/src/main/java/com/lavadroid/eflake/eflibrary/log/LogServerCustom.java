/*
package com.lavadroid.eflake.eflibrary.log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.yunji.sdk.db.DBManager;
import com.yunji.sdk.network.HttpCallback;
import com.yunji.sdk.network.NetworkUtil;
import com.yunji.sdk.network.PostAttendance;
import com.yunji.sdk.poi.FileHelper;
import com.yunji.sdk.service.LocationService;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

*/
/**
 * @Title LogServer.java
 * @Description Manager of log content thread,When network is unavailable,log
 *              content will insert into database,when network connected,it will
 *              send to server.
 * @author Eflake
 * @date 2014-6-6 下午4:15:07
 *//*

public class LogServerCustom {
	private static final String LOG_TAG = LogServerCustom.class.getSimpleName();
	*/
/**
	 * Singleton instance
	 *//*

	private static LogServerCustom mInstance;
	private static Object mLockObject = new Object();
	private static StringBuffer mBuilder;
	private boolean mStarted = false;
	*/
/**
	 * Background thread which send log content that from database when network
	 * is connected.
	 *//*

	private HandlerThread mHandlerThread;
	private Handler mHandler;
	private AtomicBoolean mRunning = new AtomicBoolean(false);
	private AtomicBoolean mSending = new AtomicBoolean(false);
	private Context context;
	private static TimerTask task;
	private static FileHelper helper;
	private boolean isSending = false;
	private long UPTATE_INTERVAL_WIFI_TIME = 1000 * 60 * 2;
	private long UPTATE_INTERVAL_OTHER_TIME = 1000 * 60 * 2;
	private String reinser_log = "re_insert=";
	private long lastUpdateTime;
	private List<LogBean> logs = new ArrayList<LogBean>();
	private Timer timer;
	private long interval;

	public LogServerCustom(Context context) {
		this.context = context;
		mHandlerThread = new HandlerThread("LogServer");
		mHandlerThread.start();
		mHandler = new Handler(mHandlerThread.getLooper());
	}

	*/
/**
	 * Singleton pattern
	 *//*

	public static LogServerCustom getInstance(Context context) {
		if (null == mInstance) {
			synchronized (mLockObject) {
				if (null == mInstance) {
					mInstance = new LogServerCustom(context);
					mBuilder = new StringBuffer();
					helper = new FileHelper(context);
				}
			}
		}

		return mInstance;
	}

	*/
/**
	 * Run this log thread,send log info to server.
	 * 
	 * @param lasttime
	 * @param endTime
	 * @param startTime
	 *//*

	public void start(final int startTime, final int endTime, int time_interval) {
		if (mStarted) {
			return;
		}
		// task = new TimerTask() {
		//
		// @Override
		// public void run() {
		// sendBlockLog(mBuilder.toString());
		// mBuilder = new StringBuilder();
		// }
		// };
		// Timer timer = new Timer();
		// timer.schedule(task, 3000);

		interval = (long) (time_interval * 60 * 1000);
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (NetworkUtil.isNetworkAvailable(context)) {
					LogBean logBean = DBManager.getInstance(context)
							.fetchLogAndRemove();
					if (null != logBean) {
						synchronized (logs) {
							logs.add(logBean);
							long currentUpdateTime = System.currentTimeMillis();
							long timeInterval = currentUpdateTime
									- lastUpdateTime;
							Time time = new Time();
							time.setToNow();
							int hour = time.hour;
							// long interval =
							if (startTime > endTime) {
								if ((hour >= startTime && hour <= 24)
										|| (hour >= 0 && hour <= endTime)) {
									if (logs.size() >= 120
											|| timeInterval > interval) {
										StringBuilder strBuilder = new StringBuilder();
										for (LogBean log : logs) {
											String insteadString = log.getContent().replace("+", "%20");
											try {
												strBuilder
														.append("uri[]=")
														.append(URLEncoder
																.encode("/x.gif?"
																		+ insteadString,
																		"UTF-8"))
														.append("&");
											} catch (UnsupportedEncodingException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
										sendBlockLog(strBuilder.toString());
										lastUpdateTime = currentUpdateTime;
										// clear the queue
										logs.clear();
									}
								}
							} else {
								if (hour >= startTime && hour <= endTime) {
									if (logs.size() >= 120
											|| timeInterval > interval) {
										StringBuilder strBuilder = new StringBuilder();
										for (LogBean log : logs) {
											try {
												strBuilder
														.append("uri[]=")
														.append(URLEncoder
																.encode("/x.gif?"
																		+ log.getContent(),
																		"UTF-8"))
														.append("&");
											} catch (UnsupportedEncodingException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
										sendBlockLog(strBuilder.toString());
										lastUpdateTime = currentUpdateTime;
										// clear the queue
										logs.clear();
									}
								}
							}
						}
					}
				}
			}
		}, 0, 50);

		*/
/*
		 * new Thread(new Runnable() { private long lastUpdateTime; private long
		 * UPTATE_INTERVAL_WIFI_TIME = 1000 * 10; private long
		 * UPTATE_INTERVAL_OTHER_TIME = 1000 * 10; private boolean isCommit =
		 * true; private int count;
		 * 
		 * @Override public void run() { while (mRunning.get()) { if
		 * (NetworkUtil.isNetworkAvailable(context)) { synchronized (mSending) {
		 * LogBean log = null; if (!mSending.get()) { log =
		 * DBManager.getInstance(context) .fetchLogAndRemove(); } if (null !=
		 * log) { // mHandler.post(new PostAttendance(context, log //
		 * .getContent(), new LogHttpCallback(log // .getContent())));
		 * mBuilder.append("uri[]=") .append(URLEncoder.encode("/x.gif?" +
		 * log.getContent())) .append("&"); // mBuilder.append(
		 * "uri[]=%2Fx.gif%3Fk%3D321%26p%3Da&uri[]=%2Fx.gif%3Fk%3D123%26p%3Db&uri[]=%2Fx.gif%3Fk%3D123%26p%3Db"
		 * ); long currentUpdateTime = System .currentTimeMillis(); long
		 * timeInterval = currentUpdateTime - lastUpdateTime; // long interval =
		 * // NetworkUtil.isWifiConnected(context)?UPTATE_INTERVAL_WIFI_TIME:
		 * UPTATE_INTERVAL_OTHER_TIME; long interval = NetworkUtil
		 * .isWifiConnected(context) ? UPTATE_INTERVAL_WIFI_TIME :
		 * UPTATE_INTERVAL_OTHER_TIME; if (timeInterval > interval) {
		 * mSending.set(true); sendBlockLog(mBuilder.toString()); mBuilder = new
		 * StringBuffer(); count = 0; isCommit = true; } if (isCommit) {
		 * lastUpdateTime = currentUpdateTime; isCommit = false; }
		 * 
		 * // DBManager.getInstance(context).deleteLog( // log.getId()); } else
		 * { Log.d(LOG_TAG, "no log"); try { Thread.sleep(1000); } catch
		 * (InterruptedException e) { e.printStackTrace(); } } } } else {
		 * Log.e(LOG_TAG, "network is unavailable"); try { Thread.sleep(1000); }
		 * catch (InterruptedException e) { e.printStackTrace(); } } }
		 * Log.e(LOG_TAG, "Log Server stopped"); } }).start();
		 *//*


		mStarted = true;
	}

	// sendBlockLog("uri[]=%2Fx.gif%3Fk%3D123%26p%3Da&uri[]=%2Fx.gif%3Fk%3D123%26p%3Db&uri[]=%2Fx.gif%3Fk%3D123%26p%3Db");

	public void sendBlockLog(String logStr) {
		final String url_param = logStr;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				HttpPost poiPost = new HttpPost("http://g.cloudtags.com.cn");
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);

				poiPost.addHeader("Content-Type",
						"application/x-www-form-urlencoded");
				try {
					poiPost.setEntity(new StringEntity(url_param));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				HttpResponse httpResponse = null;
				try {
					httpResponse = new DefaultHttpClient(httpParameters)
							.execute(poiPost);

					Log.i("logtest", "responce code="
							+ httpResponse.getStatusLine().getStatusCode());
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						Log.i("eflake_send", "***send ok***");
						// mSending.set(false);
					} else {
						Log.i("eflake", "***send failed***");
						String[] logs = url_param.split("&");
						for (int i = 0; i < logs.length; i++) {
							String log_encode = logs[i].substring(6);
							String log_decode = URLDecoder.decode(log_encode);
							Log.e(LOG_TAG, "send log failed, re-insert");
							DBManager.getInstance(context).insertLog(
									log_decode.substring(7));
							FileHelper helper = new FileHelper(context);
							reinser_log += "LOG_ENCODE = " + log_encode
									+ ",LOG_DECODE = " + log_decode
									+ "LOG_DECODE_SUB="
									+ log_decode.substring(7) + "/n";
							helper.writeSDFile(log_decode.substring(7),
									"/reinsert" + ".txt");
						}
						// mSending.set(false);
					}
				} catch (Exception e) {
					String[] logs = url_param.split("&");
					for (int i = 0; i < logs.length; i++) {
						String log_encode = logs[i].substring(6);
						String log_decode = URLDecoder.decode(log_encode);
						Log.e(LOG_TAG, "send log failed, re-insert");
						DBManager.getInstance(context).insertLog(
								log_decode.substring(7));
						FileHelper helper = new FileHelper(context);
						reinser_log += "LOG_ENCODE = " + log_encode
								+ ",LOG_DECODE = " + log_decode
								+ "LOG_DECODE_SUB=" + log_decode.substring(7)
								+ "/n";
						helper.writeSDFile(log_decode.substring(7),
								"/re_exception" + ".txt");
					}
					e.printStackTrace();
				}

			}
		};

		mHandler.post(runnable);
	}

	*/
/**
	 * Stop sending log info to server
	 *//*

	public void stop() {
		if (!mStarted) {
			return;
		}
		// mRunning.set(false);
		if (null != timer) {
			timer.cancel();
		}

		mHandlerThread.quit();
		mStarted = false;
	}

	*/
/**
	 * Put new log content into database
	 *//*

	public void put(String message) {
		Log.d(LOG_TAG, "new log: " + message);
		DBManager.getInstance(context).insertLog(message);
	}

	*/
/**
	 * @Title LogServer.java
	 * @Description Callback from server,indicated that whether log info is
	 *              sending completely.
	 * @author Eflake
	 * @date 2014-6-6 下午4:21:05
	 *//*

	private class LogHttpCallback implements HttpCallback {
		private final String mLogContent;

		public LogHttpCallback(String content) {
			mLogContent = content;
		}

		*/
/**
		 * (non-Javadoc)
		 * 
		 * @see com.yunji.sdk.network.HttpCallback#callback(int,
		 *      String, com.yunji.sdk.network.HttpCallback.Type,
		 *      Object)
		 *//*

		@Override
		public void callback(int code, String msg, Type type, Object o) {
			Log.d(LOG_TAG, "callback code: " + code);
			// if failed, we need to re-insert this log
			if (code != 200) {
				Log.e(LOG_TAG, "send log failed, re-insert");
				DBManager.getInstance(context).insertLog(mLogContent);
			}
		}
	}
}
*/
