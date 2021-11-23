package net.eunoiaym.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

public class WebCrawlerEx {
	public static void main(String[] args) throws MalformedURLException, IOException {
		// Parsing : 검수번역
		// XML Parsing ex) $.parseXML()
		// HTML Parsing

		for (int j = 2; j <= 100; j++) {
			String url = "https://search.musinsa.com/ranking/best?period=now&age=ALL&mainCategory=&subCategory=&leafCategory=&price=&golf=false&newProduct=false&exclusive=false&discount=false&soldOut=false&priceMax=&viewType=small&priceMin=&page="
					+ j;
			System.out.println(j + "페이지");
			Document doc = Jsoup.parse(new URL(url), 20000);
			// System.out.println(doc);
			Element outerUl = doc.selectFirst("#goodsRankList");
			Elements lis = outerUl.select(".li_box");
			saveHTML(j, doc.toString());
			
			if(j==1)
			return;

			System.out.println(lis.size());

			for (int i = 0; i < lis.size(); i++) {
				Element li = lis.get(i); // img-block;
				String pk = li.attr("data-goods-no");
				String link = li.selectFirst(".img-block").attr("href");
				System.out.println(link);
				// 세부페이지의 링크
				// 섬네일
				String thumbLink = li.selectFirst("img.lazyload.lazy").attr("data-original");
				System.out.println(thumbLink);

				fileDownload(pk, thumbLink);
			}
		}

	}
	
	// 제품정보 , 가격정보, 사이즈, 이미지
	static void saveHTML(int pageNum, String html) throws UnsupportedEncodingException, IOException {
		String uploadFolder= "c:\\musinsa";
		File file = new File(uploadFolder, pageNum+".html");
		FileCopyUtils.copy(html.getBytes("utf-8"), file);
		}
	

	static void fileDownload(String pk, String link) throws MalformedURLException, IOException {
		// inputstream >> transfer
		String uploadFolder = "c:\\musinsa";
		File uploadPath = new File(uploadFolder, pk);
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		InputStream is = new URL(link).openStream();
		File file = new File(uploadPath, "thumb.jpg");

		FileCopyUtils.copy(FileCopyUtils.copyToByteArray(is), file);

	}

}
