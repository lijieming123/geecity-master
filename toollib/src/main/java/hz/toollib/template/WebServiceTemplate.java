package hz.toollib.template;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.myksoap2.SoapEnvelope;
import org.myksoap2.serialization.SoapObject;
import org.myksoap2.serialization.SoapSerializationEnvelope;
import org.myksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import hz.toollib.interfaces.WebAPIListener;

/**
 * 访问webserivce的抽象类<br/>
 * 将此类文件拷贝到项目下,重命名为WebServiceAPI来使用，修改默认的url和命名空间参数。<br/>
 * 接口类继承此类，重写相关的接口方法。<br/>
 * 在子类中通过重写getInputParam、analysisOutput、getMethodName来设置接口输入、输出和接口方法名<br/>
 * 可选性重写getURL和getNAMESPACE来更改接口访问的默认URL和命名空间。<br/>
 * 输入和输出的参数作为子类成员定义，设置相关的setter和getter方法来设置输入和输出参数。
 * @author LiuPeng
 *
 */
public abstract class WebServiceTemplate {
//	http://192.168.11.81:8001/WCF/UserInfo/UserInfoMobile.asmx?wsdl
	private static final String HOSTNAME = "http://192.168.11.81:8001/WCF/";
	public static final String URL_USERINFO = HOSTNAME + "UserInfo/UserInfoMobile.asmx?wsdl";
	public static final String URL_LOCATION = HOSTNAME + "Location/LocationMobile.asmx?wsdl";
	public static final String URL_CUSTOMER = HOSTNAME + "Customer/CustomerMobile.asmx?wsdl";
	public static final String URL_ATTEND = HOSTNAME + "Attendance/AttendanceMobile.asmx?wsdl";

	public static final int SUCCESS = 1;
	public static final int CANCEL = 0;
	public static final int ERROR_NETWORK = -1;
	public static final int ERROR_IO = -2;
	public static final int ERROR_JSON = -3;
	public static final int ERROR_XML = -4;
	public static final int ERROR_CUSTOM = -5;
	public static final int ERROR_DATA = -6;

	//接口监听
	private WebAPIListener listener;
	public void setListener(WebAPIListener listener) {
		this.listener = listener;
	}

	Context context;

	//接口控制
	private boolean isCanceled = false;
	private boolean isDebug = false;
	
	public WebServiceTemplate(Context context){
		this.context = context;
	}

	/**
	 * 设置是否是调试模式，调试模式下，不进行网络访问，只执行解析方法analysisOutput，返回成功回调
	 * @param isDebug
	 */
	public void setIsDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	/**
	 * 设置是否取消任务
	 * @param isCanceled
	 */
	public void setCanceled(boolean isCanceled){
		this.isCanceled = isCanceled;
	}

	/**
	 * 获取URL
	 * @return
	 */
	protected String getURL(){
		return "http://192.168.11.180/Test/Main.asmx?wsdl";
	}
	
	/**
	 * 获取命名空间
	 * @return
	 */
	protected String getNAMESPACE(){
		return "http://tempuri.org/";
	}
	
	/**
	 * 设置输入
	 */
	abstract protected JSONObject getInputParam() throws JSONException;

	/**
	 *
	 * @param result webservice返回的字符串
	 * @return 解析是否成功
	 * @throws JSONException
	 */
	abstract protected boolean analysisOutput(String result) throws JSONException;
	
	/**
	 * 获取接口方法名
	 * @return
	 */
	abstract protected String getMethodName();
	
	/**
	 * 进行连接（异步）
	 */
	public final void doConnectInBackground(){
		new Thread(){
			@Override
			public void run(){
				if(isDebug){
					try {
						analysisOutput(null);
						sendMessage(SUCCESS,null);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return;
				}
				doConnect();
			}
		}.start();
	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(listener == null){
				return;
			}
			if(msg.what > 0){
				listener.onLoadSuccess(msg.what);
			}else if (msg.what < 0){
				listener.onLoadFail(msg.what, msg.obj.toString());
			}
		}
	};
	
	/**
	 * 非异步的连接，需要线程调用
	 */
	public final int doConnect(){
		try {
			SoapObject rpc = new SoapObject(getNAMESPACE(), getMethodName());
			rpc.addProperty("s", getInputParam().toString());
			Log.d("webservice", rpc.toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			envelope.dotNet = true;
			envelope.bodyOut = rpc;
			envelope.setOutputSoapObject(rpc);
			HttpTransportSE ht = new HttpTransportSE(getURL(), 5 * 1000);
			ht.debug = true;
			/*byte[] bytes = createRequestData(envelope,"UTF-8");
			String str = new String(bytes,"UTF-8");
			Log.d("debug","--------------------"+str);*/
			ht.call(null, envelope);
			SoapObject detail = (SoapObject) envelope.bodyIn;
			if(detail != null){
				String result = detail.getProperty(0).toString();
				Log.d("webservice", result);
				//分析接口返回的JSON方法
				if(analysisOutput(result)){
					return sendMessage(SUCCESS,null);
				}else{
					Log.e("webservice", "数据解析异常");
					return ERROR_DATA;
				}
			}
			Log.e("webservice", "网络连接异常");
			return sendMessage(ERROR_NETWORK,"网络异常，请检查网络连接");
		} catch (IOException e) {
			Log.e("webservice", "IO异常");
			e.printStackTrace();
			return sendMessage(ERROR_IO, "网络异常，请检查网络连接");
		} catch (JSONException e) {
			Log.e("webservice", "JSON解析出现异常");
			e.printStackTrace();
			return sendMessage(ERROR_JSON, "服务器返回错误信息，请联系管理员");
		} catch (XmlPullParserException e) {
			Log.e("webservice", "XML解析异常");
			e.printStackTrace();
			return sendMessage(ERROR_XML, "服务器返回错误信息，请联系管理员");
		}
	}

/*	protected byte[] createRequestData(SoapEnvelope envelope, String encoding) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(262144);
		Object result = null;
		//bos.write(this.xmlVersionTag.getBytes());
		KXmlSerializer xw = new KXmlSerializer();
		xw.setOutput(bos, encoding);
		envelope.write(xw);
		xw.flush();
		bos.write(13);
		bos.write(10);
		bos.flush();
		byte[] result1 = bos.toByteArray();
		xw = null;
		bos = null;
		return result1;
	}*/

	protected int sendMessage(int what,Object obj){
		//如果取消任务，返回CANCEL
		if(isCanceled){
			return CANCEL;
		}
		Message msg = new Message();
		msg.obj = obj;
		msg.what = what;
		handler.sendMessage(msg);
		return what;
	}
}
