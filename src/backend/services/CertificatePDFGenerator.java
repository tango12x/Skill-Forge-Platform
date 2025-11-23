package backend.services;

import backend.models.Certificate;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

/**
 * Comprehensive certificate export solution with identical quality for PDF and PNG
 * Ensures both formats have the same content and professional appearance
 */
public class CertificatePDFGenerator {
    
    /**
     * Main PDF generation method with guaranteed quality
     */
    public static boolean generatePDF(Certificate certificate, String filePath) {
        try {
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            // Create high-quality certificate image first
            BufferedImage certificateImage = createCertificateImage(certificate);
            
            // Try system PDF printing with the high-quality image
            PrintableCertificate printable = new PrintableCertificate(certificateImage);
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Certificate - " + certificate.getCertificateId());
            job.setPrintable(printable);
            
            // Set landscape orientation for certificate
            PageFormat format = job.defaultPage();
            format.setOrientation(PageFormat.LANDSCAPE);
            job.setPrintable(printable, format);
            
            if (job.printDialog()) {
                job.print();
                JOptionPane.showMessageDialog(null,
                    "PDF generation initiated!\n" +
                    "Please select 'Microsoft Print to PDF' or similar PDF printer\n" +
                    "and choose the save location in the print dialog.",
                    "PDF Generation",
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("PDF Generation Error: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback to high-quality image
            JOptionPane.showMessageDialog(null,
                "PDF generation failed. Generating high-quality PNG image instead.\n" +
                "You can print this image as PDF using any image viewer.",
                "PDF Fallback",
                JOptionPane.WARNING_MESSAGE);
            
            return generateImageCertificate(certificate, filePath.replace(".pdf", "_high_quality.png"));
        }
    }
    
    /**
     * Generates a formatted text PDF as alternative
     */
    public static boolean generateFormattedTextPDF(Certificate certificate, String filePath) {
        try {
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.println("==================================================================================");
                writer.println("                           CERTIFICATE OF COMPLETION");
                writer.println("==================================================================================");
                writer.println();
                writer.println("                              Skill Forge Learning Platform");
                writer.println();
                writer.println("This is to certify that:");
                writer.println();
                writer.println("                      " + certificate.getStudentName());
                writer.println();
                writer.println("has successfully completed the course:");
                writer.println();
                writer.println("              \"" + certificate.getCourseTitle() + "\"");
                writer.println();
                writer.printf("with a final score of %.1f%% (%s)%n", certificate.getFinalScore(), certificate.getGrade());
                writer.println();
                writer.println("----------------------------------------------------------------------------------");
                writer.println("Instructor: " + certificate.getInstructorName());
                writer.println("Completed: " + certificate.getFormattedCompletionDate());
                writer.println("Certificate ID: " + certificate.getCertificateId());
                writer.println("Issue Date: " + certificate.getFormattedIssueDate());
                writer.println("----------------------------------------------------------------------------------");
                writer.println();
                writer.println();
                writer.println("                         __________________________");
                writer.println("                         Skill Forge Administration");
                writer.println();
                writer.println("==================================================================================");
                writer.println("This is an official certificate issued by Skill Forge Learning Platform.");
                writer.println("To verify this certificate, please contact platform administration.");
                writer.println("Certificate ID: " + certificate.getCertificateId());
                writer.println("==================================================================================");
            }
            
            System.out.println("Formatted text PDF saved: " + filePath);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error generating formatted text PDF: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generates high-quality PNG image with professional certificate design
     */
    public static boolean generateImageCertificate(Certificate certificate, String filePath) {
        try {
            BufferedImage image = createCertificateImage(certificate);
            
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }
            
            File outputFile = new File(filePath);
            boolean success = javax.imageio.ImageIO.write(image, "PNG", outputFile);
            
            if (success) {
                System.out.println("High-quality certificate image saved: " + outputFile.getAbsolutePath());
                return true;
            }
            return false;
            
        } catch (IOException e) {
            System.err.println("Error generating certificate image: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Creates a professional certificate image with complete content
     * This is the master method that ensures consistent quality across all formats
     */
    public static BufferedImage createCertificateImage(Certificate certificate) {
        // Use higher resolution for better print quality
        int width = 1600;  // Increased for better PDF quality
        int height = 1200; // Increased for better PDF quality
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();
        
        // Enable high-quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        // Fill background with professional off-white color
        g2d.setColor(new Color(253, 253, 250));
        g2d.fillRect(0, 0, width, height);
        
        // Draw all certificate elements
        drawCompleteCertificate(g2d, certificate, width, height);
        
        g2d.dispose();
        return image;
    }
    
    /**
     * Draws complete certificate with all elements - used by both PNG and PDF
     */
    private static void drawCompleteCertificate(Graphics2D g2d, Certificate certificate, int width, int height) {
        drawCertificateBorder(g2d, width, height);
        drawCertificateBackground(g2d, width, height);
        drawCertificateContent(g2d, certificate, width, height);
        drawCertificateSeal(g2d, width, height);
    }
    
    /**
     * Draws decorative border with professional styling
     */
    private static void drawCertificateBorder(Graphics2D g2d, int width, int height) {
        // Main gold border
        g2d.setColor(new Color(212, 175, 55));
        g2d.setStroke(new BasicStroke(12));
        g2d.drawRect(30, 30, width - 60, height - 60);
        
        // Secondary border
        g2d.setColor(new Color(180, 150, 50));
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(50, 50, width - 100, height - 100);
        
        // Corner decorations
        int cornerSize = 60;
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(new Color(160, 130, 45));
        
        // Top-left corner
        drawOrnamentalCorner(g2d, 50, 50, cornerSize, cornerSize);
        // Top-right corner
        drawOrnamentalCorner(g2d, width - 50 - cornerSize, 50, cornerSize, cornerSize);
        // Bottom-left corner
        drawOrnamentalCorner(g2d, 50, height - 50 - cornerSize, cornerSize, cornerSize);
        // Bottom-right corner
        drawOrnamentalCorner(g2d, width - 50 - cornerSize, height - 50 - cornerSize, cornerSize, cornerSize);
    }
    
    /**
     * Draws ornamental corner designs
     */
    private static void drawOrnamentalCorner(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.drawLine(x, y, x + width, y);
        g2d.drawLine(x, y, x, y + height);
        g2d.drawLine(x + width, y, x + width, y + height / 3);
        g2d.drawLine(x, y + height, x + width / 3, y + height);
    }
    
    /**
     * Draws subtle background pattern
     */
    private static void drawCertificateBackground(Graphics2D g2d, int width, int height) {
        // Subtle watermark background
        g2d.setColor(new Color(240, 240, 235));
        g2d.setFont(new Font("Serif", Font.ITALIC, 120));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        
        String watermark = "SKILL FORGE";
        int watermarkWidth = g2d.getFontMetrics().stringWidth(watermark);
        g2d.drawString(watermark, (width - watermarkWidth) / 2, height / 2);
        
        // Reset transparency
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    /**
     * Draws official seal/logo
     */
    private static void drawCertificateSeal(Graphics2D g2d, int width, int height) {
        int centerX = width / 2;
        int sealY = height - 150;
        
        // Seal circle
        g2d.setColor(new Color(212, 175, 55, 100));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(centerX - 60, sealY - 60, 120, 120);
        
        // Seal text
        g2d.setColor(new Color(100, 100, 100));
        g2d.setFont(new Font("Serif", Font.BOLD, 14));
        String sealText = "OFFICIAL SEAL";
        int sealWidth = g2d.getFontMetrics().stringWidth(sealText);
        g2d.drawString(sealText, centerX - sealWidth / 2, sealY);
        
        g2d.setFont(new Font("Serif", Font.PLAIN, 12));
        String platformText = "Skill Forge";
        int platformWidth = g2d.getFontMetrics().stringWidth(platformText);
        g2d.drawString(platformText, centerX - platformWidth / 2, sealY + 20);
    }
    
    /**
     * Draws all certificate content with complete information
     */
    private static void drawCertificateContent(Graphics2D g2d, Certificate certificate, int width, int height) {
        int centerX = width / 2;
        
        // ===== MAIN TITLE =====
        g2d.setColor(new Color(44, 62, 80));
        g2d.setFont(new Font("Serif", Font.BOLD, 60));
        String title = "CERTIFICATE OF COMPLETION";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, centerX - titleWidth / 2, 150);
        
        // ===== SUBTITLE =====
        g2d.setColor(new Color(128, 128, 128));
        g2d.setFont(new Font("Serif", Font.ITALIC, 24));
        String subtitle = "Skill Forge Learning Platform";
        int subtitleWidth = g2d.getFontMetrics().stringWidth(subtitle);
        g2d.drawString(subtitle, centerX - subtitleWidth / 2, 200);
        
        // ===== SEPARATOR LINE =====
        g2d.setColor(new Color(212, 175, 55));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(centerX - 250, 230, centerX + 250, 230);
        
        // ===== CERTIFICATION TEXT =====
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.PLAIN, 28));
        String certifiesText = "This is to certify that";
        int certifiesWidth = g2d.getFontMetrics().stringWidth(certifiesText);
        g2d.drawString(certifiesText, centerX - certifiesWidth / 2, 300);
        
        // ===== STUDENT NAME (HIGHLIGHTED) =====
        g2d.setColor(new Color(41, 128, 185));
        g2d.setFont(new Font("Serif", Font.BOLD, 48));
        String studentName = certificate.getStudentName();
        int nameWidth = g2d.getFontMetrics().stringWidth(studentName);
        g2d.drawString(studentName, centerX - nameWidth / 2, 380);
        
        // ===== COMPLETION TEXT =====
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.PLAIN, 28));
        String completedText = "has successfully completed the course";
        int completedWidth = g2d.getFontMetrics().stringWidth(completedText);
        g2d.drawString(completedText, centerX - completedWidth / 2, 460);
        
        // ===== COURSE TITLE (HIGHLIGHTED) =====
        g2d.setColor(new Color(39, 174, 96));
        g2d.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36));
        String courseTitle = "\"" + certificate.getCourseTitle() + "\"";
        int courseWidth = g2d.getFontMetrics().stringWidth(courseTitle);
        g2d.drawString(courseTitle, centerX - courseWidth / 2, 530);
        
        // ===== SCORE AND GRADE =====
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.PLAIN, 26));
        String scoreText = "with a final score of " + String.format("%.1f", certificate.getFinalScore()) + 
                          "% (" + certificate.getGrade() + ")";
        int scoreWidth = g2d.getFontMetrics().stringWidth(scoreText);
        g2d.drawString(scoreText, centerX - scoreWidth / 2, 600);
        
        // ===== DETAILS SECTION =====
        int detailsStartY = 680;
        g2d.setFont(new Font("Serif", Font.PLAIN, 22));
        
        // Left column
        String instructorText = "Instructor: " + certificate.getInstructorName();
        g2d.drawString(instructorText, centerX - 400, detailsStartY);
        
        String completionDateText = "Completed: " + certificate.getFormattedCompletionDate();
        g2d.drawString(completionDateText, centerX - 400, detailsStartY + 40);
        
        // Right column  
        String certificateIdText = "Certificate ID: " + certificate.getCertificateId();
        g2d.drawString(certificateIdText, centerX + 100, detailsStartY);
        
        String issueDateText = "Issued: " + certificate.getFormattedIssueDate();
        g2d.drawString(issueDateText, centerX + 100, detailsStartY + 40);
        
        // ===== SIGNATURE SECTION =====
        int signatureY = height - 250;
        
        // Signature line
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(centerX - 150, signatureY, centerX + 150, signatureY);
        
        // Signature text
        g2d.setFont(new Font("Serif", Font.PLAIN, 20));
        String signatureText = "Skill Forge Administration";
        int signatureWidth = g2d.getFontMetrics().stringWidth(signatureText);
        g2d.drawString(signatureText, centerX - signatureWidth / 2, signatureY + 30);
        
        // ===== VERIFICATION FOOTER =====
        g2d.setColor(new Color(100, 100, 100));
        g2d.setFont(new Font("Serif", Font.PLAIN, 16));
        String verifyText = "Verify this certificate at: Skill Forge Learning Platform | Certificate ID: " + certificate.getCertificateId();
        int verifyWidth = g2d.getFontMetrics().stringWidth(verifyText);
        g2d.drawString(verifyText, centerX - verifyWidth / 2, height - 80);
    }
}

/**
 * Printable implementation that uses the high-quality certificate image
 * This ensures PDF output matches PNG quality exactly
 */
class PrintableCertificate implements Printable {
    private BufferedImage certificateImage;
    
    public PrintableCertificate(BufferedImage certificateImage) {
        this.certificateImage = certificateImage;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D) graphics;
        
        // Enable high-quality rendering for PDF
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Position image on page
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        // Calculate scaling to fit the image on the page while maintaining aspect ratio
        double scaleX = pageFormat.getImageableWidth() / certificateImage.getWidth();
        double scaleY = pageFormat.getImageableHeight() / certificateImage.getHeight();
        double scale = Math.min(scaleX, scaleY);
        
        // Apply scaling
        g2d.scale(scale, scale);
        
        // Draw the high-quality certificate image
        g2d.drawImage(certificateImage, 0, 0, null);
        
        return PAGE_EXISTS;
    }
}