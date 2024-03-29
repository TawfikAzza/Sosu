package gui.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.*;
import bll.MedicineListManger;
import bll.exceptions.*;
import bll.util.GlobalVariables;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gui.Controller.FunctionalReportViewController;
import gui.Model.GIReportModel;
import gui.Model.ReportModel;
import javafx.util.Pair;

public class GeneratePdf {
    private static String FILE = "c:/temp/FirstPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static FunctionalReportViewController functionalReportViewController;

    public static void generateFunctionalReportPDF(HashMap<Integer, java.util.List<Pair<AbilityCategory, Ability>>> hashMap, File file) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addFunctionalContent(document, hashMap);
            document.close();
        } catch (Exception | HealthCategoryException | CitizenReportException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    public static void generateConditionReportPDF(HashMap<Integer, java.util.List<Pair<HealthCategory, Condition>>> hashMap, File file) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addConditionContent(document, hashMap);
            document.close();
        } catch (Exception | HealthCategoryException | CitizenReportException e) {
            DisplayMessage.displayError(e);
            e.printStackTrace();
        }
    }

    private static void addConditionContent(Document document, HashMap<Integer, List<Pair<HealthCategory, Condition>>> hashMap) throws DocumentException, HealthCategoryException, CitizenReportException {
        Anchor anchor = new Anchor("Health Report for citizen : "
                + GlobalVariables.getSelectedCitizen().getFName()
                + " " + GlobalVariables.getSelectedCitizen().getLName(), catFont);
        anchor.setName("Functional Report");
        ReportModel reportModel = new ReportModel();
        HashMap<Integer, HealthCategory> categoryHashMap = reportModel.getAllConditionsMainCategories();
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);


        for (Map.Entry<Integer, java.util.List<Pair<HealthCategory, Condition>>> entry : hashMap.entrySet()) {
            Integer sid = entry.getKey();
            Paragraph subPara = new Paragraph(categoryHashMap.get(sid).getName(), subFont);
            java.util.List<Pair<HealthCategory, Condition>> list = entry.getValue();
            Section subCatPart = catPart.addSection(subPara);
            //subCatPart.addSection(categoryHashMap.get(sid).getName());

            subCatPart.add(new Paragraph("\n"));
            createConditionTable(subCatPart, list);

        }
        document.add(catPart);
    }

    private static void createConditionTable(Section subCatPart, List<Pair<HealthCategory, Condition>> list) throws DocumentException {
        for (Pair<HealthCategory, Condition> pair : list) {
            PdfPTable table = new PdfPTable(2);
            table.setSpacingAfter(10);


            PdfPCell c1 = new PdfPCell(new Phrase("Category Name"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(c1);
            table.addCell(pair.getKey().getName());


            c1 = new PdfPCell(new Phrase("Important Notes"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell(pair.getValue().getImportantNote());


            c1 = new PdfPCell(new Phrase("Status"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getStatus());


            c1 = new PdfPCell(new Phrase("Assessment"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getAssessement());


            c1 = new PdfPCell(new Phrase("Citizen Goal"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getGoal());


            c1 = new PdfPCell(new Phrase("Expected Score"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getExpectedScore());


            c1 = new PdfPCell(new Phrase("Visit Date"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getVisitDate());

            c1 = new PdfPCell(new Phrase("Observations"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getObservation());

            table.setHeaderRows(1);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3});
            subCatPart.add(table);
        }
    }

    private static void addFunctionalContent(Document document, HashMap<Integer, java.util.List<Pair<AbilityCategory, Ability>>> hashMap) throws DocumentException, HealthCategoryException, CitizenReportException {
        Anchor anchor = new Anchor("Functional Report for citizen : "
                + GlobalVariables.getSelectedCitizen().getFName()
                + " " + GlobalVariables.getSelectedCitizen().getLName(), catFont);
        anchor.setName("Functional Report");
        ReportModel reportModel = new ReportModel();
        HashMap<Integer, AbilityCategory> categoryHashMap = reportModel.getAllFAMainCategories();
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);


        for (Map.Entry<Integer, java.util.List<Pair<AbilityCategory, Ability>>> entry : hashMap.entrySet()) {
            Integer sid = entry.getKey();
            Paragraph subPara = new Paragraph(categoryHashMap.get(sid).getName(), subFont);
            java.util.List<Pair<AbilityCategory, Ability>> list = entry.getValue();
            Section subCatPart = catPart.addSection(subPara);
            //subCatPart.addSection(categoryHashMap.get(sid).getName());

            subCatPart.add(new Paragraph("\n"));
            createFunctionalTable(subCatPart, list);

        }
        document.add(catPart);

    }

    private static void createFunctionalTable(Section subCatPart, java.util.List<Pair<AbilityCategory, Ability>> list)
            throws DocumentException {


        for (Pair<AbilityCategory, Ability> pair : list) {
            PdfPTable table = new PdfPTable(2);
            table.setSpacingAfter(10);


            PdfPCell c1 = new PdfPCell(new Phrase("Category Name"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(c1);
            table.addCell(pair.getKey().getName());


            c1 = new PdfPCell(new Phrase("Important Notes"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell(pair.getValue().getImportantNote());


            c1 = new PdfPCell(new Phrase("Status"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getStatus());


            c1 = new PdfPCell(new Phrase("Performance Assessment"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getPerformance());


            c1 = new PdfPCell(new Phrase("Meaning"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getMeaning());


            c1 = new PdfPCell(new Phrase("Citizen Goal"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getGoals());


            c1 = new PdfPCell(new Phrase("Score"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getScore());


            c1 = new PdfPCell(new Phrase("Expected Score"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getExpectedScore());


            c1 = new PdfPCell(new Phrase("Visit Date"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getVisitDate());

            c1 = new PdfPCell(new Phrase("Observations"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell("" + pair.getValue().getObservation());

            table.setHeaderRows(1);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3});
            subCatPart.add(table);
        }
    }


    public static void generateGIReport(HashMap<String, String> hashMap, File file) throws FileNotFoundException {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addGIReportContent(document, hashMap);
            document.close();
        } catch (DocumentException | CitizenException | GIReportException e) {
            e.printStackTrace();
        }
    }

    private static void addGIReportContent(Document document, HashMap<String, String> hashMap) throws CitizenException, GIReportException, DocumentException {
        Anchor anchor = new Anchor("General Information Report:  "
                + "\n"
                + GlobalVariables.getSelectedCitizen().getFName()
                + " " + GlobalVariables.getSelectedCitizen().getLName()
                + " \n" + GlobalVariables.getSelectedCitizen().getBirthDate(), catFont);
        anchor.setName("General Information Report");
        GIReportModel giReportModel = new GIReportModel();
        HashMap<String, String> categoryHashMap = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        catPart.add(new Paragraph("\n"));
        createGIReportContent(catPart, hashMap);
        document.add(catPart);

    }


    private static void createGIReportContent(Section subCatPart, HashMap<String, String> hashMap)
            throws DocumentException {

        PdfPTable table1 = new PdfPTable(2);
        table1.setSpacingAfter(1);
        String Category = "Category Name";
        String Content = "Content";

        PdfPCell c11 = new PdfPCell(new Phrase(Category));
        PdfPCell c12 = new PdfPCell(new Phrase(Content));

        table1.addCell(c11);
        table1.addCell(c12);

        subCatPart.add(table1);


        PdfPTable table = new PdfPTable(2);
        for (Map.Entry entry : hashMap.entrySet()) {

            table.setSpacingAfter(10);

            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            PdfPCell c1 = new PdfPCell(new Phrase(key));
            PdfPCell c2 = new PdfPCell(new Phrase(value));

            table.addCell(c1);
            table.addCell(c2);

            table.setHeaderRows(1);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3});

        }
        subCatPart.add(table);
    }


    public static void generateMedicineReport(String mlist, File file) throws FileNotFoundException {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addMReportContent(document, mlist);
            document.close();
        } catch (DocumentException | CitizenException | GIReportException | MedicineListException e) {
            e.printStackTrace();
        }
    }

    private static void addMReportContent(Document document, String mlist) throws CitizenException, GIReportException, DocumentException, MedicineListException {
        Anchor anchor = new Anchor("Medicine List "
                + "\n"
                + GlobalVariables.getSelectedCitizen().getFName()
                + " " + GlobalVariables.getSelectedCitizen().getLName()
                + " \n" + GlobalVariables.getSelectedCitizen().getBirthDate(), catFont);
        anchor.setName("Medicine List");
        GIReportModel giReportModel = new GIReportModel();
        HashMap<String, String> categoryHashMap = giReportModel.getGiReportManger(GlobalVariables.getSelectedCitizen());
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        catPart.add(new Paragraph("\n"));
        createMReportContent(catPart, mlist);
        document.add(catPart);

    }


    private static void createMReportContent(Section subCatPart, String mlist)
            throws DocumentException, MedicineListException {

        PdfPTable table1 = new PdfPTable(1);
        table1.setSpacingAfter(1);
        MedicineListManger medicineList = new MedicineListManger() ;

        //String Content = (String) medicineList.getMedicineList(GlobalVariables.getSelectedCitizen());

        PdfPCell c12 = new PdfPCell(new Phrase(String.valueOf(medicineList.getMedicineList(GlobalVariables.getSelectedCitizen()))));

        table1.addCell(c12);
        subCatPart.add(table1);
    }

}
