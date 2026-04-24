package com.example.rayanlabmanagement.reports;

import com.example.rayanlabmanagement.entity.Patient;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ReportService {
    public byte[] generatePatientReport(List<Patient> patients) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/patient_report.jrxml");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patients);
        Map<String, Object> params = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
