package frontend;

import backend.models.*;
import backend.services.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Swing frame for displaying certificates with PDF export functionality
 * Provides a professional certificate display with option to save as PDF
 * 
 * Features:
 * - Elegant certificate display with professional styling
 * - PDF export button with file chooser
 * - Responsive layout for different certificate sizes
 * - Status feedback for PDF generation
 */
public class CertificateViewerFrame extends JFrame {
    private Certificate certificate;
    private JEditorPane certificateDisplay;
    private JButton btnExportPDF;
    private JButton btnClose;

    /**
     * Constructor for creating certificate viewer window
     * 
     * @param certificate The certificate to display and export
     */
    public CertificateViewerFrame(Certificate certificate) {
        this.certificate = certificate;
        initializeUI();
        setupEventHandlers();
    }

    /**
     * Initializes all UI components and layouts
     * Creates the main certificate display area and control buttons
     */
    private void initializeUI() {
        // Set window properties
        setTitle("Certificate - " + certificate.getCertificateId());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null); // Center on screen
        setResizable(true);

        // Create main panel with border
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Create certificate display area
        certificateDisplay = new JEditorPane();
        certificateDisplay.setContentType("text/html");
        certificateDisplay.setText(certificate.toHTMLFormat());
        certificateDisplay.setEditable(false);
        certificateDisplay.setBackground(Color.WHITE);

        // Wrap in scroll pane for large certificates
        JScrollPane scrollPane = new JScrollPane(certificateDisplay);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        scrollPane.setPreferredSize(new Dimension(650, 450));

        // Create button panel
        JPanel buttonPanel = createButtonPanel();

        // Add components to main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set content and make visible
        setContentPane(mainPanel);
        pack(); // Adjust window size to fit content
    }

    /**
     * Creates the control panel with action buttons
     * 
     * @return JPanel containing export and close buttons
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);

        // Create PDF export button
        btnExportPDF = new JButton("Export as PDF");
        btnExportPDF.setFont(new Font("Arial", Font.BOLD, 14));
        btnExportPDF.setBackground(new Color(52, 152, 219)); // Blue color
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFocusPainted(false);
        btnExportPDF.setToolTipText("Save certificate as PDF file");

        // Create close button
        btnClose = new JButton("Close");
        btnClose.setFont(new Font("Arial", Font.PLAIN, 14));
        btnClose.setBackground(new Color(149, 165, 166)); // Gray color
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);

        // Add buttons to panel
        panel.add(btnExportPDF);
        panel.add(btnClose);

        return panel;
    }

    /**
     * Sets up event handlers for buttons
     * Handles PDF export and window closing
     */
    private void setupEventHandlers() {
        // PDF export button handler
        btnExportPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportCertificateAsPDF();
            }
        });

        // Close button handler
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
    }

    /**
     * Handles PDF export process with file chooser and status feedback
     * Guides user through saving certificate as PDF file
     */
    private void exportCertificateAsPDF() {
        try {
            // Show enhanced option dialog
            String[] options = {
                    "High-Quality PDF (Recommended)",
                    "High-Quality PNG Image",
                    "Text PDF Document",
                    "Cancel"
            };

            int choice = JOptionPane.showOptionDialog(this,
                    "Choose export format:\n\n" +
                            "• High-Quality PDF: Best for printing and sharing\n" +
                            "• PNG Image: Maximum quality, can be printed as PDF\n" +
                            "• Text PDF: Simple document version",
                    "Export Certificate",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 3 || choice == JOptionPane.CLOSED_OPTION) {
                return; // User cancelled
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Certificate");

            String baseFilename = "Certificate_" +
                    certificate.getStudentName().replace(" ", "_") + "_" +
                    certificate.getCourseTitle().replace(" ", "_");

            String suggestedFilename = baseFilename;
            String fileFilterDescription = "";

            switch (choice) {
                case 0: // High-Quality PDF
                    suggestedFilename += ".pdf";
                    fileFilterDescription = "PDF Files (*.pdf)";
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                            fileFilterDescription, "pdf"));
                    break;

                case 1: // PNG Image
                    suggestedFilename += ".png";
                    fileFilterDescription = "PNG Image Files (*.png)";
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                            fileFilterDescription, "png"));
                    break;

                case 2: // Text PDF
                    suggestedFilename += ".pdf";
                    fileFilterDescription = "PDF Files (*.pdf)";
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                            fileFilterDescription, "pdf"));
                    break;
            }

            fileChooser.setSelectedFile(new File(suggestedFilename));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                boolean success = false;
                String message = "";

                switch (choice) {
                    case 0: // High-Quality PDF
                        success = CertificatePDFGenerator.generatePDF(certificate, filePath);
                        message = success ? "High-quality PDF generation initiated!\n\n" +
                                "Please follow these steps:\n" +
                                "1. Select 'Microsoft Print to PDF' as printer\n" +
                                "2. Choose save location in the print dialog\n" +
                                "3. The PDF will have the same quality as the PNG version"
                                : "PDF generation failed or was cancelled.";
                        break;

                    case 1: // PNG Image
                        success = CertificatePDFGenerator.generateImageCertificate(certificate, filePath);
                        message = success ? "High-quality PNG image saved successfully!\n\n" +
                                "You can print this image as PDF using:\n" +
                                "• Right-click → Print → Select 'Microsoft Print to PDF'\n" +
                                "• Or open in any image viewer and use Print to PDF"
                                : "Failed to generate high-quality image.";
                        break;

                    case 2: // Text PDF
                        success = CertificatePDFGenerator.generateFormattedTextPDF(certificate, filePath);
                        message = success ? "Text PDF document generated successfully!"
                                : "Failed to generate text PDF.";
                        break;
                }

                JOptionPane.showMessageDialog(this, message,
                        success ? "Export Successful" : "Export Failed",
                        success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error during export: " + ex.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Displays the certificate viewer window
     * Makes the frame visible and brings it to front
     */
    public void showCertificate() {
        setVisible(true);
        toFront(); // Bring window to front
    }

    /**
     * Main method for testing the certificate viewer
     * Creates a sample certificate and displays it
     */
    public static void main(String[] args) {
        // Test certificate creation and display
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create sample certificate for testing
                    Certificate testCertificate = new Certificate(
                            "STU123",
                            "John Smith",
                            "CS101",
                            "Introduction to Programming",
                            "Dr. Jane Anderson",
                            92.5);

                    // Create and display certificate viewer
                    CertificateViewerFrame viewer = new CertificateViewerFrame(testCertificate);
                    viewer.showCertificate();

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error creating certificate viewer: " + e.getMessage(),
                            "Initialization Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}