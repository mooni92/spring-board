package net.eunoiaym.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mchange.v2.util.PropertiesUtils;


import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.BoardVo;
import net.eunoiaym.domain.Criteria;
import net.eunoiaym.mapper.BoardMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class Sample3Tests {
	@Setter @Autowired
	private BoardMapper mapper;
	
	@Test
	public void tetGetListPaging() {
		Criteria cri =  new Criteria();
		
		mapper.getListWithPaging(cri).forEach(log::info);
		// 글번호 제목 작성자 작성일
		
	}
	public static Map<String, Object> extendsBeanUtils(Object bean) {
		Map<String, Object> map = null;
		try {
			map = PropertyUtils.describe(bean);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	@Test
	public void testBeanUtils() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Map<String,Object>> result = mapper.getListWithPaging(new Criteria())
				.stream().map(Sample3Tests::extendsBeanUtils).collect(Collectors.toList());
		
		List<String> mapNames = Arrays.asList(new String[]{"bno", "titile" ,"writer","regDate"});
		mapper.getListWithPaging(new Criteria())
			.stream().map(Sample3Tests::extendsBeanUtils).collect(Collectors.toList()).forEach(map ->{
				mapNames.forEach(name -> log.info(map.get(name)));
			});
	}
	
	@Test
	public void testCreateExcelByBoard() throws IOException {
		List<Map<String,Object>> list = mapper.getListWithPaging(new Criteria())
				.stream().map(Sample3Tests::extendsBeanUtils).collect(Collectors.toList());
		List<String> mapNames = Arrays.asList(new String[]{"bno", "titile" ,"writer","regDate"});
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("board Sheet");
		Cell cell = getCell(sheet, 0, 0);
		
		cell.setCellValue("글번호");
		
		cell = getCell(sheet, 0 , 1);
		cell.setCellValue("제목");
		
		cell = getCell(sheet, 0 , 2);
		cell.setCellValue("작성자");
		
		cell = getCell(sheet, 0 , 3);
		cell.setCellValue("작성일");
		
		for(int i=1; i< list.size(); i++) {
			for(int j = 1 ; j < mapNames.size(); j++) {
				cell = getCell(sheet, i+1, j);
				cell.setCellValue(list.get(i).get(mapNames.get(j)).toString());
				if(j == 3) {
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(workbook.createDataFormat().getFormat("yy/mm/dd hh:mm"));
					cell.setCellStyle(style);
					
				}
			}
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		FileOutputStream stream = new FileOutputStream("d:\\board.xlsx");
		workbook.write(stream);
	}
		
		
	@Test
	public void testReadExcel() throws IOException {
		FileInputStream stream = new FileInputStream("d:\\score.xlsx");
		Workbook workbook = new XSSFWorkbook(stream);
		Sheet sheet = workbook.getSheetAt(0);
		List<String> mapNames = new ArrayList<>();
		for(int i = 0;  ; i++) {
			String name = getCell(sheet, 0 ,i).getStringCellValue();
			if(name.equals("")) break;
				mapNames.add(name);
		}
		
		log.info(mapNames);
		
		List<Map<String, Object>> list = new ArrayList<>();
		log.info(getCell(sheet, 10, 10).getCellType());
		for(int i = 0; ; i++){
		Map<String, Object> map = new HashMap<>();
		if(getCell(sheet, i+1, 0).getCellType() == CellType.BLANK) break;
		for(int j =0; j< mapNames.size(); j++) {
			CellType type = getCell(sheet, i+1 , j ).getCellType();
			switch (type) {
			case NUMERIC: case FORMULA:
				map.put(mapNames.get(j), (int)(getCell(sheet, i+1, j).getNumericCellValue()));
				break;
			case STRING:
				map.put(mapNames.get(j), getCell(sheet, i+1, j).getStringCellValue());
				break;
			default:
				break;
				}
			}
			log.info(map);
		}
		
	}

		
	
	

	
	// row 취득
	public Row getRow(Sheet sheet, int rownum) {
		Row row = sheet.getRow(rownum);
		if(row == null) {
			row = sheet.createRow(rownum);
		}
		return row;
	}
	
	// cell 취득
	public Cell getCell(Row row, int cellnum) {
		Cell cell = row.getCell(cellnum);
		if(cell == null) {
			cell = row.createCell(cellnum);
		}
		return cell;
	}
	
	public Cell getCell(Sheet sheet, int rownum, int cellnum) {
		Row row = getRow(sheet,rownum);
		return getCell(row, cellnum);
	}
	
	
}