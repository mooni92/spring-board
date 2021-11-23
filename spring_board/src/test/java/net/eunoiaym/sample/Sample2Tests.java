package net.eunoiaym.sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.eunoiaym.domain.Criteria;
import net.eunoiaym.mapper.BoardMapper;

 // 엑셀 만들기
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class Sample2Tests {
	@Setter @Autowired
	private BoardMapper mapper;
	
	@Test
	public void tetGetListPaging() {
		Criteria cri =  new Criteria();
		
		mapper.getListWithPaging(cri).forEach(log::info);
	}
	
	@Test
	public void testCreateExcel() throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("TEST Sheet");
		
		Cell cell = getCell(sheet, 0, 0);
		cell.setCellValue("Test Value");
		
		cell = getCell(sheet, 0 , 1);
		cell.setCellValue(100);
		
		cell = getCell(sheet, 0 , 2);
		cell.setCellValue(Calendar.getInstance());
		
		// 1행 A, B, C 
		
		// style
		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(workbook.createDataFormat().getFormat("yy/mm/dd hh:mm"));
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.TOP);
		style.setFillBackgroundColor(IndexedColors.BLUE.index);
		org.apache.poi.ss.usermodel.Font font = workbook.createFont();
		font.setColor(IndexedColors.WHITE.index);
		cell.setCellStyle(style);
		
		// 셀 너비
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		
		cell = getCell(sheet, 1, 0);
		cell.setCellValue(1);
		cell = getCell(sheet, 1, 1);
		cell.setCellValue(2);
		cell = getCell(sheet, 1, 2);
		cell.setCellFormula("SUM(A2:B2)");
		
		FileOutputStream stream = new FileOutputStream("d:\\test.xlsx");
		workbook.write(stream);
		
		
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
