import com.wry.constant.R;
import com.wry.util.BrowserPhantomjs;

public class Test {
	public static void main(String[] args) throws Exception {
		String currentURL = System.getProperty("user.dir");
		R.CURRENTURL = currentURL;
		System.setProperty("logUrl", currentURL);
		String url = "http://s.weibo.com/weibo/%25E5%258D%2581%25E4%25B9%259D%25E5%25A4%25A7&Refer=index";
		BrowserPhantomjs bp = new BrowserPhantomjs();
		String soundCode = bp.httpGet(url);
		System.out.println(soundCode);
	}
}
