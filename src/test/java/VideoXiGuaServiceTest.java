import java.util.ArrayList;
import java.util.List;

import com.wry.log.LogUtil;
import com.wry.model.Discuss;
import com.wry.parser.XiGuaParser;
import com.wry.util.BrowserHttpclientXIGua;

public class VideoXiGuaServiceTest {

	public static void main(String[] args) {
		int offset = 0;
		XiGuaParser parser = new XiGuaParser();
		List<Discuss> discussList = new ArrayList<>();
		while (true) {
			try {
				String url = "https://is.snssdk.com/article/v2/tab_comments/?group_id=6505538272381370894&item_id=6505538272381370894&aggr_type=1&count=20&offset="
						+ offset
						+ "&tab_index=0&iid=25377382466&device_id=47753959844&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=639&version_name=6.3.9&device_platform=android&ab_version=271217%2C236847%2C246276%2C273539%2C271584%2C217285%2C249822%2C268198%2C227960%2C249631%2C273774%2C252883%2C255490%2C252979%2C257292%2C249824%2C249819%2C249827%2C249830%2C273215%2C264627%2C272859%2C275010%2C274099%2C150151&ssmix=a&device_type=vivo+Y51e&device_brand=vivo&language=zh&os_api=22&os_version=5.1.1&uuid=862063039571455&openudid=3375f0196b3f6b72&manifest_version_code=239&resolution=540*960&dpi=240&update_version_code=6392&_rticket=1517810590116&fp=zlTqLSPMcWs5FlP5LSU1FYwIFlK1&ts=1517810586&as=a2a5ff578a19daf3a78704&mas=000903536cf86832899fe09fe571a8b3c0da1f2e7211d50b0d7757";
				BrowserHttpclientXIGua browser = new BrowserHttpclientXIGua(url);
				String soundCode = browser.httpGet();
				if (!parser.parserDisscussList(soundCode, discussList)) {
					break;
				}
				offset += 20;
			} catch (Exception e) {
				LogUtil.LogInfo("西瓜视频解析视频评论时出错" + e.getMessage());
			}
		}
		// for (Discuss discuss : discussList) {
		// discuss.setVideoXigua(video);
		// discussService.save(discuss);
		// textAreaConsole.append(discuss + "\n");
		// }
	}
}
