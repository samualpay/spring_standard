package com.samual.standard.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.ReportExportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Created by Samual on 2018/8/14.
 */
@Controller
public class JasperController {
    @GetMapping("/report/show/jrxml")//透過jrxml 檔案載入模板 速度較慢 因為需要compile
    public void showJrmxl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        try {
            File file = new ClassPathResource("jasper/First_Report.jrxml").getFile();
            InputStream inputStream = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            servletOutputStream = response.getOutputStream();
            byte[] pdf = JasperRunManager.runReportToPdf(jasperReport, new HashMap<String, Object>(), new JREmptyDataSource());
            servletOutputStream.write(pdf);
            response.setContentType("application/pdf");
        } catch (Exception e) {
            servletOutputStream.print(e.getMessage());
        }
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    @GetMapping("/report/show/jasper")//透過jasper 檔案載入模板
    public void showJasper(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        try {
            File file = new ClassPathResource("jasper/First_Report.jasper").getFile();
            InputStream inputStream = new FileInputStream(file);
            JasperRunManager.runReportToPdfStream(inputStream, servletOutputStream, new HashMap<>(), new JREmptyDataSource());
            response.setContentType("application/pdf");
        } catch (Exception e) {
            servletOutputStream.print(e.getMessage());
        }
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    @GetMapping("/report/download/{type}")
    public ResponseEntity download(@PathVariable String type) {
        try {
            File file = new ClassPathResource("jasper/First_Report.jrxml").getFile();
            InputStream inputStream = new FileInputStream(file);
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), new JREmptyDataSource());
            JRAbstractExporter jrAbstractExporter = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ReportExportConfiguration reportExportConfiguration = null;
            String fileName = "";
            switch (type) {
                case "PDF":
                    jrAbstractExporter = new JRPdfExporter();
                    fileName = "donwload.pdf";
                    break;
                case "XLS":
                    jrAbstractExporter = new JRXlsExporter();
                    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                    configuration.setDetectCellType(true);
                    configuration.setCollapseRowSpan(false);
                    configuration.setWhitePageBackground(false);
                    reportExportConfiguration = configuration;
                    fileName = "donwload.xls";
                    break;
                case "XLSX":
                    jrAbstractExporter = new JRXlsxExporter();
                    SimpleXlsxReportConfiguration configurationx = new SimpleXlsxReportConfiguration();
                    configurationx.setDetectCellType(true);
                    configurationx.setCollapseRowSpan(false);
                    configurationx.setWhitePageBackground(false);
                    reportExportConfiguration = configurationx;
                    fileName = "donwload.xlsx";
                    break;
//                case "CSV":
//                    jrAbstractExporter = new JRCsvExporter();
//                    SimpleCsvExporterConfiguration simpleCsvExporterConfiguration = new SimpleCsvExporterConfiguration();
//                    simpleCsvExporterConfiguration.setWriteBOM(true);
//                    simpleCsvExporterConfiguration.setOverrideHints(true);
//                    simpleCsvExporterConfiguration.setRecordDelimiter("\r\n");
//                    jrAbstractExporter.setConfiguration( new SimpleCsvExporterConfiguration());
//                    fileName= "donwload.csv";
//                    break;
            }
            if (reportExportConfiguration != null) {
                jrAbstractExporter.setConfiguration(reportExportConfiguration);
            }
            jrAbstractExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            jrAbstractExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            jrAbstractExporter.exportReport();
            jrAbstractExporter.reset();
            byte[] data = byteArrayOutputStream.toByteArray();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            httpHeaders.add("Content-Disposition", "attachment;filename=\"" + fileName + "\";filename*=utf-8''" + fileName);
            return new ResponseEntity(data, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
