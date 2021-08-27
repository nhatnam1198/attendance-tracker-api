package com.example.demo.Service;

import Util.Const;
import com.example.demo.Model.AttendanceDetails;
import com.example.demo.Model.Event;
import com.example.demo.Model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class StatisticsService {
    @Autowired
    EventService eventService;
    public byte[] getStatisticsSheet(Integer subjectClassId, String email) {
        ArrayList<Event> eventArrayList = eventService.getEventListBySubjectClassId(subjectClassId, email);
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 6000);

        Row header = sheet.createRow(3);
        // merge cells
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 2, 2));

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 3));

        CellStyle titleStyle = workbook.createCellStyle();
        Row title = sheet.createRow(0);
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Cell titleCell = title.createCell(1);
        String subjectClassName = eventArrayList.get(0).getSubjectClass().getName();
        LocalDate eventDate = eventArrayList.get(0).getDateTime();
        int eventYearDate = eventDate.getYear();
//        String classYearDate = new SimpleDateFormat("yyyy").format();
        titleCell.setCellValue("Điểm danh lớp " +  subjectClassName + " Năm " + eventYearDate);
        titleCell.setCellStyle(titleStyle);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        writeStudentList(sheet, style, eventArrayList);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("TT");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Họ và tên");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Mã sinh viên");
        headerCell.setCellStyle(headerStyle);

        File currDir = new File("src/main/resources/XlsxFiles/");
        String path = currDir.getAbsolutePath();
        String fileLocation = path + "/temp_" + System.currentTimeMillis()+ ".xlsx";
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(fileLocation));
            return fileBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    private void writeStudentList(Sheet sheet, CellStyle style, ArrayList<Event> eventArrayList) {
        List<AttendanceDetails> attendanceDetailList = eventArrayList.get(0).getAttendanceDetailsList();
        attendanceDetailList.sort(new Comparator<AttendanceDetails>() {
            @Override
            public int compare(AttendanceDetails o1, AttendanceDetails o2) {
                return o1.getAttendance().getStudent().getName().compareTo(o2.getAttendance().getStudent().getName());
            }
        });
        int numberOfStudents = 0;
        for (int j = 0; j < attendanceDetailList.size(); j++) {
            numberOfStudents++;
            Student student = attendanceDetailList.get(j).getAttendance().getStudent();
            Row row = sheet.createRow(6 + j);
            Cell cell = row.createCell(0);
            cell.setCellValue(j + 1);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(student.getName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(student.getStudentCode());
            cell.setCellStyle(style);
        }
        Row row3 = sheet.getRow(3);
        Row row4 = sheet.createRow(4);
        Row row5 = sheet.createRow(5);
        for (int i = 0; i < eventArrayList.size(); i++) {
            for(int j = 0; j< numberOfStudents; j++){
                Row row = sheet.getRow(6+ j);
                Cell cell = row.createCell(3+i);
                cell.setCellStyle(style);
            }
        }

        style.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i < eventArrayList.size(); i++) {
            Cell cell = row3.createCell(3 + i);
            cell.setCellValue(i + 1);
            cell.setCellStyle(style);

            cell = row4.createCell(3+i);
            cell.setCellValue(eventArrayList.get(i).getShift().getName());
            cell.setCellStyle(style);

            cell = row5.createCell(3 + i);
            LocalDate eventDate = eventArrayList.get(i).getDateTime();
            cell.setCellValue(eventDate.getDayOfMonth() + "/" + eventDate.getMonthValue());
            cell.setCellStyle(style);
            attendanceDetailList = eventArrayList.get(i).getAttendanceDetailsList();
            attendanceDetailList.sort(new Comparator<AttendanceDetails>() {
                @Override
                public int compare(AttendanceDetails o1, AttendanceDetails o2) {
                    return o1.getAttendance().getStudent().getName().compareTo(o2.getAttendance().getStudent().getName());
                }
            });
            for (int j = 0; j < attendanceDetailList.size(); j++) {
                int status = attendanceDetailList.get(j).getStatus();
                Row row = sheet.getRow(6 + j);
                if(row == null){
                    row = sheet.createRow(6+j);
                }

                cell = row.createCell(3 + i);
//                if(cell == null){
//                    i =3;
//                }
                if(status == Const.ATTENDED){
                    cell.setCellValue("X");
                }else if(status == Const.ABSENT){
                    cell.setCellValue("V");
                }else if(status == Const.ALLOWED){
                    cell.setCellValue("P");
                }else{
                    cell.setCellValue("XN");
                }
                cell.setCellStyle(style);
            }
        }
    }
}
