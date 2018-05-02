package com.weibokey.main;

import org.junit.Test;

public class WeiboKeyMainTest {

	@Test
	public void testWeiboKeyMain() {
		String url = "http://s.weibo.com/weibo/Ê®¾Å´ó&xsort=hot&hasvideo=1&searchvideo=1&Refer=STopic_box";
		WeiboKeyMain weiboKeyMain = new WeiboKeyMain();
		weiboKeyMain.getVideos(url);
	}

}
