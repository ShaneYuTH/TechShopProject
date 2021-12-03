package com.techshop.admin.user.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.techshop.admin.AbstractExporter;
import com.techshop.common.entity.User;

/**
 * The Class UserExcelExporter. Generate Excel file using Apache POI,
 */
public class UserExcelExporter extends AbstractExporter {
	
	/** The workbook. */
	private XSSFWorkbook workbook;
	
	/** The sheet. */
	private XSSFSheet sheet;
	
	/**
	 * Instantiates a new user excel exporter.
	 */
	public UserExcelExporter() {
		workbook = new XSSFWorkbook();
	}
	
	/**
	 * Export user list to Excel file.
	 *
	 * @param listUsers The user list
	 * @param response The response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "users_");
		
		writeHeaderLine();
		writeDataLines(listUsers);
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
	
	/**
	 * Write data lines.
	 *
	 * @param listUsers The list users
	 */
	private void writeDataLines(List<User> listUsers) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(false);
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		for (User user : listUsers) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			
			createCell(row, columnIndex++, user.getId(), cellStyle);
			createCell(row, columnIndex++, user.getEmail(), cellStyle);
			createCell(row, columnIndex++, user.getFirstName(), cellStyle);
			createCell(row, columnIndex++, user.getLastName(), cellStyle);
			createCell(row, columnIndex++, user.getRoles().toString(), cellStyle);
			createCell(row, columnIndex++, user.isEnabled(), cellStyle);
		}
		
	}

	/**
	 * Write header line.
	 */
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Users");
		XSSFRow row = sheet.createRow(0);
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);
		
		createCell(row, 0, "User Id", cellStyle);
		createCell(row, 1, "E-mail", cellStyle);
		createCell(row, 2, "First Name", cellStyle);
		createCell(row, 3, "Last Name", cellStyle);
		createCell(row, 4, "Roles", cellStyle);
		createCell(row, 5, "Enabled", cellStyle);
		
	}
	
	/**
	 * Creates the cell.
	 *
	 * @param row The row in excel file
	 * @param columnIndex The excel file column index
	 * @param value The cell value
	 * @param style The cell style
	 */
	private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);			
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);	
		} else {
			cell.setCellValue((String) value);	
		}

		cell.setCellStyle(style);
	}
}
