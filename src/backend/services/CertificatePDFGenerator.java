package backend.services;

import backend.models.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.File;

/**
 * Service class for generating professional PDF certificates
 * Uses iText PDF library to create high-quality certificate documents
 * 
 * Dependencies: iTextPDF library (would need to be added to project)
 * Note: This implementation assumes iTextPDF is available
 * 
 * Features:
 * - Professional certificate layout with borders
 * - Custom fonts and styling
 * - Institutional branding
 * - Score and grade display
 * - Digital signature area
 */
public class CertificatePDFGenerator {
    
    // PDF styling constants
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL, BaseColor.GRAY);
    private static final Font NAME_FONT = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.BLUE);
    private static final Font COURSE_FONT = new Font(Font.FontFamily.HELVETICA, 20, Font.ITALIC, BaseColor.GREEN);
    private static final Font DETAIL_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
    private static final Font SCORE_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.RED);
    
    /**
     * Generates a professional PDF certificate from certificate data
     * Creates a beautifully formatted PDF file ready for printing or digital distribution
     * 
     * @param certificate The certificate data to convert to PDF
     * @param filePath The destination path for the PDF file
     * @return true if PDF generation successful, false otherwise
     */
    public static boolean generatePDF(Certificate certificate, String filePath) {
        Document document = new Document(PageSize.A4.rotate()); // Landscape orientation
        
        try {
            // Initialize PDF writer
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
            // Add decorative border
            addCertificateBorder(document);
            
            // Add institutional header
            addInstitutionalHeader(document);
            
            // Add main certificate content
            addCertificateContent(document, certificate);
            
            // Add footer with signatures
            addCertificateFooter(document, certificate);
            
            document.close();
            return true;
            
        } catch (Exception e) {
            System.err.println("PDF Generation Error: " + e.getMessage());
            e.printStackTrace();
            if (document.isOpen()) {
                document.close();
            }
            return false;
        }
    }
    
    /**
     * Adds a decorative border around the certificate
     * Creates a professional appearance with institutional colors
     */
    private static void addCertificateBorder(Document document) throws DocumentException {
        // Create a table for the border effect
        PdfPTable borderTable = new PdfPTable(1);
        borderTable.setWidthPercentage(95);
        borderTable.setSpacingBefore(20f);
        borderTable.setSpacingAfter(20f);
        
        PdfPCell borderCell = new PdfPCell();
        borderCell.setBorderWidth(3f);
        borderCell.setBorderColor(BaseColor.GOLD);
        borderCell.setPadding(30f);
        borderCell.setBackgroundColor(new BaseColor(250, 250, 250)); // Light gray background
        
        borderTable.addCell(borderCell);
        document.add(borderTable);
    }
    
    /**
     * Adds institutional header with logo and title
     * Provides professional branding for the certificate
     */
    private static void addInstitutionalHeader(Document document) throws DocumentException {
        // Main title
        Paragraph title = new Paragraph("CERTIFICATE OF COMPLETION", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10f);
        document.add(title);
        
        // Subtitle
        Paragraph subtitle = new Paragraph("Skill Forge Learning Platform", SUBTITLE_FONT);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(30f);
        document.add(subtitle);
        
        // Separator line
        addSeparatorLine(document);
    }
    
    /**
     * Adds the main certificate content including student and course information
     * Formats the core certificate data in an elegant layout
     */
    private static void addCertificateContent(Document document, Certificate certificate) 
            throws DocumentException {
        
        // Student name section
        Paragraph certifies = new Paragraph("This is to certify that", DETAIL_FONT);
        certifies.setAlignment(Element.ALIGN_CENTER);
        certifies.setSpacingAfter(10f);
        document.add(certifies);
        
        Paragraph studentName = new Paragraph(certificate.getStudentName(), NAME_FONT);
        studentName.setAlignment(Element.ALIGN_CENTER);
        studentName.setSpacingAfter(10f);
        document.add(studentName);
        
        // Course completion section
        Paragraph completed = new Paragraph("has successfully completed the course", DETAIL_FONT);
        completed.setAlignment(Element.ALIGN_CENTER);
        completed.setSpacingAfter(10f);
        document.add(completed);
        
        Paragraph courseTitle = new Paragraph("\"" + certificate.getCourseTitle() + "\"", COURSE_FONT);
        courseTitle.setAlignment(Element.ALIGN_CENTER);
        courseTitle.setSpacingAfter(20f);
        document.add(courseTitle);
        
        // Score and grade section
        Paragraph score = new Paragraph(
            "with a final score of " + String.format("%.1f", certificate.getFinalScore()) + 
            "% (" + certificate.getGrade() + ")", SCORE_FONT);
        score.setAlignment(Element.ALIGN_CENTER);
        score.setSpacingAfter(30f);
        document.add(score);
    }
    
    /**
     * Adds certificate footer with details and signature area
     * Includes completion date, certificate ID, and institutional signature
     */
    private static void addCertificateFooter(Document document, Certificate certificate) 
            throws DocumentException {
        
        // Create details table
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(80);
        detailsTable.setSpacingBefore(20f);
        
        // Instructor information
        PdfPCell instructorCell = new PdfPCell(new Phrase(
            "Instructor: " + certificate.getInstructorName() + "\n" +
            "Completed: " + certificate.getFormattedCompletionDate(), DETAIL_FONT));
        instructorCell.setBorder(Rectangle.NO_BORDER);
        instructorCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        // Certificate ID
        PdfPCell idCell = new PdfPCell(new Phrase(
            "Certificate ID: " + certificate.getCertificateId() + "\n" +
            "Issue Date: " + certificate.getFormattedIssueDate(), DETAIL_FONT));
        idCell.setBorder(Rectangle.NO_BORDER);
        idCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        detailsTable.addCell(instructorCell);
        detailsTable.addCell(idCell);
        document.add(detailsTable);
        
        // Signature area
        Paragraph signature = new Paragraph("\n\n\n__________________________\nSkill Forge Administration", DETAIL_FONT);
        signature.setAlignment(Element.ALIGN_CENTER);
        signature.setSpacingBefore(40f);
        document.add(signature);
    }
    
    /**
     * Adds a decorative separator line between sections
     * Enhances visual appeal and section separation
     */
    private static void addSeparatorLine(Document document) throws DocumentException {
        Paragraph line = new Paragraph("________________________________________________________________");
        line.setAlignment(Element.ALIGN_CENTER);
        line.setSpacingAfter(20f);
        document.add(line);
    }
    
    /**
     * Alternative PDF generation method with simplified layout
     * Useful for systems without iTextPDF or for basic requirements
     * 
     * @param certificate The certificate data
     * @param filePath Destination file path
     * @return true if successful
     */
    public static boolean generateSimplePDF(Certificate certificate, String filePath) {
        // This would be a fallback implementation
        // Could use Java's built-in PDF capabilities or alternative libraries
        System.out.println("Simple PDF generation not implemented - using full version");
        return generatePDF(certificate, filePath);
    }
}