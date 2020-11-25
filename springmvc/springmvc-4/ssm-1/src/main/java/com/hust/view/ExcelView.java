package com.hust.view;

import com.hust.entity.Article;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        List<Article> articles= (List<Article>) model.get("articles");

        HSSFSheet sheet = workbook.createSheet("文章列表");//创建一页
        HSSFRow header = sheet.createRow(0);//创建第一行
        header.createCell(0).setCellValue("标题");
        header.createCell(1).setCellValue("正文");
        for( int i = 0; i < articles.size();i++){
            HSSFRow row = sheet.createRow(i + 1);
            Article article = articles.get(i);
            row.createCell(0).setCellValue(article.getTitle());
            row.createCell(1).setCellValue(article.getContent());
        }
    }
}
