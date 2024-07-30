package com.ruoyi.attachment.service;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.junit.Test;

public class WordToPdfServiceDemo {

    @Test
    public void test4() {
        String wordPath = "/Users/chenzh/mywork/2022/5/ruoyi-vue-kms项目资料/kms设计规划.docx";
        String pdfPath = "/Users/chenzh/mywork/2022/5/ruoyi-vue-kms项目资料/kms设计规划.pdf";
        try {
            //新建一个pdf文档
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            //Address是将要被转化的word文档
            Document doc = new Document(wordPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PDF);
            os.close();
            System.out.println("转换完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() throws Exception {
        String sourceFilePath = "/Users/chenzh/mywork/2022/5/南网投入(1).xlsx";
        String desFilePathd = "/Users/chenzh/mywork/2022/5/南网投入(1).pdf";
        Workbook wb = new Workbook(sourceFilePath);// 原始excel路径

        FileOutputStream fileOS = new FileOutputStream(desFilePathd);
        PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
        pdfSaveOptions.setOnePagePerSheet(true);
        wb.save(fileOS, pdfSaveOptions);
        fileOS.flush();
        fileOS.close();
        System.out.println("转换完毕");
    }


    @Test
    public void test6() {
        if (!getLicensePpt()) {
            System.out.println("非法------------");
            return;
        }
        String pptPath = "/Users/chenzh/mywork/2022/5/Nipic_31794475_20200626203920903000_20220530094035A002.pptx";
        String pdfPath = "/Users/chenzh/mywork/2022/5/Nipic_31794475_20200626203920903000_20220530094035A002.pptx2.pdf";
        try {
            //新建一个pdf文档
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            //Address是将要被转化的word文档
            //Document doc = new Document(wordPath);
            Presentation ppt = new Presentation(pptPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            //doc.save(os, SaveFormat.PDF);
            ppt.save(os, com.aspose.slides.SaveFormat.Pdf);
            os.close();
            System.out.println("转换完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证License 若不验证则转化出的pdf文档会有水印产生
     * @return
     */
    public boolean getLicensePpt(){
        boolean result = false;
        try {

            InputStream is =this.getClass().getClassLoader().getResourceAsStream("license.xml");
            //注意此处为对应aspose-slides的jar包
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();

            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
