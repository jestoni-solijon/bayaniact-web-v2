package com.bayaniact.common.file;

import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.repository.ResidentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    private final ResidentRepository residentRepository; // Replace with your repository

    public ExcelService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    public void exportResidentsToExcel(HttpServletResponse response) throws IOException {
        // Set the response headers for Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Residents.xlsx");

        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Residents");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "First Name", "Last Name", "Email", "Address", "Contact"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Fetch data from database
        List<Resident> residents = residentRepository.findAll();

        // Populate rows with resident data
        int rowIndex = 1; // Start from the second row
        for (Resident resident : residents) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(resident.getResidentId());
            row.createCell(1).setCellValue(resident.getFirstName());
            row.createCell(2).setCellValue(resident.getLastName());
            row.createCell(3).setCellValue(resident.getEmail());
            row.createCell(4).setCellValue(resident.getAddress());
            row.createCell(5).setCellValue(resident.getContactNumber());
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to the response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}