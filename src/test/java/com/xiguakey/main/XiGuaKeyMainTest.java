package com.xiguakey.main;

import java.util.List;

import org.junit.Test;

import com.xiguakey.model.Video;

public class XiGuaKeyMainTest {

	@Test
	public void testXiGuaKeyMain() {
		XiGuaKeyMain xiGuaKeyMain = new XiGuaKeyMain();
		List<Video> videoList = xiGuaKeyMain.getVideos("��");
		for(Video video : videoList) {
			System.out.println(video);
		}
	}

}
