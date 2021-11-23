package net.eunoiaym.service;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.GoodsVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class GoodsServiceTests {
	@Setter
	@Autowired
	private GoodsService service;

	String uploadFolder = "c:\\musinsa";

	@Test
	public void testExist() {
		assertNotNull(service);
	}

	@Test
	public void testCrawl() throws MalformedURLException, IOException {
		// Parsing : 검수번역
		// XML Parsing ex) $.parseXML()
		// HTML Parsing

		for (int j = 1; j <= 1; j++) {
			String url = "https://search.musinsa.com/ranking/best?period=now&age=ALL&mainCategory=&subCategory=&leafCategory=&price=&golf=false&newProduct=false&exclusive=false&discount=false&soldOut=false&priceMax=&viewType=small&priceMin=&page=1"
					+ 1000000;
			System.out.println(j + "페이지");
			Document doc = Jsoup.parse(new URL(url), 20000);
			// System.out.println(doc);
			Element outerUl = doc.selectFirst("#goodsRankList");
			Elements lis = outerUl.select(".li_box");
			saveHTML(j, doc.toString());

			if (j == 1)
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

	   @Test
	   public void testCrawlDetail() throws MalformedURLException,IOException{
	      File file = new File(uploadFolder);
	      Stream.of(file.listFiles(File::isDirectory)).sorted().forEach(dir->{
	      
	         String pk = dir.getName();
	         if(!pk.equals("2081554")){
	         try{
	            String url = String.format("https://store.musinsa.com/app/goods/%s?loc=goods_rank",pk);
	            /*log.info(url);*/
	            Document doc = Jsoup.parse(new URL(url),20000);
	            /*log.info(doc);*/
	            
	            File html = new File(dir, "detail.html");
	            if(!html.exists()) {
	            FileCopyUtils.copy(doc.toString().getBytes("utf-8"), html);
	            }
	            String title = doc.selectFirst(".section_product_summary span.product_title").text();
//	            log.info(title);
	            String brand = doc.selectFirst(".product_article .product_article_contents a").text();
//	            log.info(brand);
	            Integer price = new DecimalFormat("#,###원").parse(doc.selectFirst("#goods_price").text()).intValue();
//	            log.info(price);
	            
	            
	            Elements lis = doc.select("#detail_thumb li:not(.video_thumb)");
	            List<java.util.Map<String, Object>> list = new ArrayList<>();
	            for(int i =0; i<lis.size(); i++) {
	            	
	            	String thumbLink = lis.get(i).selectFirst("img").attr("src");
//	               log.info(li.selectFirst("img").attr("src"));
	            	String originLink = lis.get(i).attr("id").equals("org_bigimg") ?  
	            			thumbLink.replace("_60", "_900") : thumbLink.replace("_60", "_500");
	            			
	            			thumbLink = "https:" + thumbLink;
	            			originLink = "https:" + originLink;
	            			
//	            	log.info(originLink);
	            	
	            	// 파일 저장
	            	// 원본 ex) 1.jpg
	            	File origin = new File(file+"/"+pk, i+1+".jpg");
	            	FileCopyUtils.copy(FileCopyUtils.copyToByteArray(new URL(originLink).openStream())
	            			, origin);
	            	// 섬네일 ex) s_1.jpg
	            	File thumb = new File(file+"/"+pk,"s_"+(i+1)+".jpg");
	            	FileCopyUtils.copy(FileCopyUtils.copyToByteArray(new URL(thumbLink).openStream())
	            			, thumb);
	            	// map 입력
	            	Map<String, Object> map = new HashMap<>();
	            	map.put("bno", pk);
	            	map.put("ordered", i+1);
	            	list.add(map);
	            }
	               
	            GoodsVo vo = new GoodsVo();
	            vo.setBno(Integer.parseInt(pk));
	            vo.setBrand(brand);
	            vo.setPrice(price);
	            vo.setTitle(title);
	            vo.setAttachs(list);
	            service.register(vo);
	         }
	      catch (Exception e) {
	         // TODO: handle exception
	         }
	      }
	         log.info(pk+ "완료");
	   });
	}
	
	@Test
	public void testFormat() throws ParseException {
		DecimalFormat df = new DecimalFormat("#,###원");
		Integer v = df.parse("299,999원").intValue();
		log.info(v);
		
		
	}

	void saveHTML(int pageNum, String html) throws UnsupportedEncodingException, IOException {
		String uploadFolder = "c:\\musinsa";
		File file = new File(uploadFolder, pageNum + ".html");
		FileCopyUtils.copy(html.getBytes("utf-8"), file);
	}

	void fileDownload(String pk, String link) throws MalformedURLException, IOException {
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
