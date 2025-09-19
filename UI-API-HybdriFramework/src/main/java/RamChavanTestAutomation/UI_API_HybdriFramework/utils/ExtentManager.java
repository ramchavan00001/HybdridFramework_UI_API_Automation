package RamChavanTestAutomation.UI_API_HybdriFramework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    /**
     * Create ExtentReports instance (singleton).
     */
    public synchronized static ExtentReports createInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setReportName("UI + API Hybrid Automation Report");
            spark.config().setDocumentTitle("Automation Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    /**
     * Get the current ExtentReports instance.
     */
    public synchronized static ExtentReports getReporter() {
        return extent;
    }

    /**
     * Flush reports to disk.
     */
    public synchronized static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
