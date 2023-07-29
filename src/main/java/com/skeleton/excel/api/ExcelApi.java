package com.skeleton.excel.api;

import com.skeleton.board.service.BoardService;
import com.skeleton.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
public class ExcelApi {
    private ExcelService excelService;
    private BoardService boardService;
    @Autowired
    public ExcelApi(ExcelService excelService, BoardService boardService) {
        this.excelService = excelService;
        this.boardService = boardService;
    }

    @GetMapping("/excelDownload")
    public ResponseEntity excelDownload(HttpServletResponse response) {
        boardService.excelDownload(response);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/upload.do")
    public Map<String, String> excelUploadAjax(MultipartHttpServletRequest request) throws Exception {
        Map<String, String> result = new HashMap<>();
        MultipartFile excelFile = request.getFile("excelFile");

        try {
            if(excelFile != null && !excelFile.isEmpty()) {
                File destFile = new File("C:\\upload\\"+excelFile.getOriginalFilename());
                excelFile.transferTo(destFile);
                excelService.excelUpload(destFile);
                result.put("code", "1");
                result.put("msg", "업로드 성공");
                Files.delete(destFile.toPath());
            } else {
                result.put("code", "0");
                result.put("msg", "업로드 실패");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
