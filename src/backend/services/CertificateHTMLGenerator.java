package backend.services;

import backend.models.Certificate;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates HTML files that can be printed as PDF using browser's "Print to PDF" feature
 * This leverages the user's web browser for PDF generation
 */
public class CertificateHTMLGenerator {
    
    /**
     * Generates an HTML file that can be printed as PDF from browser
     */
    public static boolean generateHTMLCertificate(Certificate certificate, String filePath) {
        try {
            // Ensure HTML extension
            if (!filePath.toLowerCase().endsWith(".html")) {
                filePath += ".html";
            }
            
            String htmlContent = generateHTMLContent(certificate);
            
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(htmlContent);
            }
            
            System.out.println("HTML certificate saved: " + filePath);
            
            // Optionally open in default browser
            openInBrowser(filePath);
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error generating HTML certificate: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generates professional HTML content for the certificate
     */
    private static String generateHTMLContent(Certificate certificate) {
        return "<!DOCTYPE html>\n" +
               "<html>\n" +
               "<head>\n" +
               "    <title>Certificate - " + certificate.getCertificateId() + "</title>\n" +
               "    <style>\n" +
               "        body { \n" +
               "            font-family: Arial, sans-serif; \n" +
               "            margin: 0; \n" +
               "            padding: 40px; \n" +
               "            background: linear-gradient(to bottom, #fefefe, #f5f5f5);\n" +
               "        }\n" +
               "        .certificate { \n" +
               "            border: 15px solid #D4AF37; \n" +
               "            padding: 50px; \n" +
               "            text-align: center; \n" +
               "            background: white;\n" +
               "            box-shadow: 0 0 20px rgba(0,0,0,0.1);\n" +
               "            max-width: 800px;\n" +
               "            margin: 0 auto;\n" +
               "        }\n" +
               "        .title { \n" +
               "            color: #2c3e50; \n" +
               "            font-size: 36px; \n" +
               "            margin-bottom: 10px; \n" +
               "            font-weight: bold;\n" +
               "        }\n" +
               "        .subtitle { \n" +
               "            color: #7f8c8d; \n" +
               "            font-size: 18px; \n" +
               "            margin-bottom: 30px; \n" +
               "        }\n" +
               "        .student-name { \n" +
               "            color: #2980b9; \n" +
               "            font-size: 32px; \n" +
               "            margin: 20px 0; \n" +
               "            font-weight: bold;\n" +
               "        }\n" +
               "        .course-title { \n" +
               "            color: #27ae60; \n" +
               "            font-size: 24px; \n" +
               "            margin: 20px 0; \n" +
               "            font-style: italic;\n" +
               "        }\n" +
               "        .score { \n" +
               "            color: #e74c3c; \n" +
               "            font-size: 20px; \n" +
               "            margin: 20px 0; \n" +
               "            font-weight: bold;\n" +
               "        }\n" +
               "        .details { \n" +
               "            margin-top: 40px; \n" +
               "            text-align: left; \n" +
               "            display: inline-block;\n" +
               "        }\n" +
               "        .signature { \n" +
               "            margin-top: 60px; \n" +
               "            border-top: 2px solid black; \n" +
               "            padding-top: 10px; \n" +
               "            display: inline-block;\n" +
               "        }\n" +
               "        @media print {\n" +
               "            body { margin: 0; padding: 0; }\n" +
               "            .certificate { border: 10px solid #D4AF37; box-shadow: none; }\n" +
               "        }\n" +
               "    </style>\n" +
               "</head>\n" +
               "<body>\n" +
               "    <div class=\"certificate\">\n" +
               "        <div class=\"title\">CERTIFICATE OF COMPLETION</div>\n" +
               "        <div class=\"subtitle\">Skill Forge Learning Platform</div>\n" +
               "        <hr style=\"border: 1px solid #D4AF37; width: 80%; margin: 20px auto;\">\n" +
               "        \n" +
               "        <p>This is to certify that</p>\n" +
               "        <div class=\"student-name\">" + certificate.getStudentName() + "</div>\n" +
               "        <p>has successfully completed the course</p>\n" +
               "        <div class=\"course-title\">\"" + certificate.getCourseTitle() + "\"</div>\n" +
               "        <div class=\"score\">with a final score of " + String.format("%.1f", certificate.getFinalScore()) + "% (" + certificate.getGrade() + ")</div>\n" +
               "        \n" +
               "        <div class=\"details\">\n" +
               "            <p><strong>Instructor:</strong> " + certificate.getInstructorName() + "</p>\n" +
               "            <p><strong>Completed on:</strong> " + certificate.getFormattedCompletionDate() + "</p>\n" +
               "            <p><strong>Certificate ID:</strong> " + certificate.getCertificateId() + "</p>\n" +
               "            <p><strong>Issue Date:</strong> " + certificate.getFormattedIssueDate() + "</p>\n" +
               "        </div>\n" +
               "        \n" +
               "        <div class=\"signature\">\n" +
               "            Skill Forge Administration\n" +
               "        </div>\n" +
               "        \n" +
               "        <div style=\"margin-top: 30px; font-size: 12px; color: #95a5a6;\">\n" +
               "            To verify this certificate, visit Skill Forge Platform\n" +
               "        </div>\n" +
               "    </div>\n" +
               "    \n" +
               "    <script>\n" +
               "        // Auto-print when opened (optional)\n" +
               "        // window.onload = function() { window.print(); }\n" +
               "    </script>\n" +
               "</body>\n" +
               "</html>";
    }
    
    /**
     * Opens the HTML file in the system's default browser
     * User can then use browser's "Print to PDF" feature
     */
    private static void openInBrowser(String filePath) {
        try {
            File htmlFile = new File(filePath);
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            System.err.println("Could not open browser: " + e.getMessage());
        }
    }
}