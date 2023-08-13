package com.skeleton.excel.service;

import com.skeleton.board.repository.BoardRepository;
import com.skeleton.board.vo.Board;
import com.skeleton.excel.ExcelRead;
import com.skeleton.excel.ExcelReadOption;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService{
    BoardRepository boardRepository;

    @Autowired
    public ExcelServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }



    @Override
    public void excelUpload(File destiFile) throws Exception {
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destiFile.getAbsolutePath()); //파일경로 추가

//        excelReadOption.setOutputColumns(list); //추출할 컬럼명 추가
        excelReadOption.setStartRow(2); //시작행(헤더부분 제외)

        List<Map<String, String>> excelContent = ExcelRead.read(excelReadOption);

        for(Map<String, String> article: excelContent){
//            DAO.excelUpload(article);
            System.out.println("article = " + article);
        }

    }

    @Override
    public String excelDate(File destFile) throws Exception {
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
        excelReadOption.setStartRow(2); //시작행(헤더부분 제외)

        List<Map<String, String>> excelContent  = ExcelRead.read(excelReadOption);
        return excelContent.get(0).get("A");
    }

    @Override
    public void excelDownload(HttpServletResponse response) {
        List<Board> boardList = boardRepository.findAll();
        createBoardExcelDownloadResponse(response, boardList);
    }

    private void createBoardExcelDownloadResponse(HttpServletResponse response, List<Board> boardList) {
        try{
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("게시물");

            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            // 파일명
            final String fileName = "게시물 통계.xlsx";

            // 헤더
            final String[] header = {"번호", "제목", "내용", "글쓴이", "이름"};
            Row row = sheet.createRow(0);

            final int headerLength = header.length;
            for (int i = 0; i < headerLength; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            // 바디
            final int boardList_size = boardList.size();
            for (int i = 0; i < boardList_size; i++) {
                row = sheet.createRow(i+1); // 헤더
                Board board = boardList.get(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(board.getBno());
                cell.setCellStyle(numberCellStyle);

                cell = row.createCell(1);
                cell.setCellValue(board.getTitle());

                cell = row.createCell(2);
                cell.setCellValue(board.getContent());
            }

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
