package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.ini4j.Wini;


import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller{


    @FXML
    private TextArea center_text;
    @FXML
    private ListView website_list;
    @FXML
    private ListView tool_list;
    @FXML
    private ListView settings_list;
    @FXML
    private Accordion accord;
    @FXML
    private TitledPane launcher_pane;
    @FXML
    private ProgressBar progress_bar;

    public void startButton() throws IOException, URISyntaxException {
        int size = 0;

        Wini ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\tools.ini"));
        size += ini.size();
        ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\websites.ini"));
        size += ini.size();
        ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\settings.ini"));
        size += ini.size();

        int progress = 0;

        progress_bar.setProgress(progress);
        //Open websites
        ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\websites.ini"));
        for (String website: ini.keySet()) {
            progress += 1/size;
            if(ini.get(website, "active", boolean.class)){
                Desktop.getDesktop().browse(new URI(ini.get(website, "websiteURL", String.class)));
            }
            progress_bar.setProgress(progress);
        }


        //Open game
        ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\settings.ini"));
        if(ini.get("PoE Steam", "active", boolean.class)){
            progress += 1/size;
            URI uri = new URI("steam://run/238960");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
            progress_bar.setProgress(progress);
        }
        ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\settings.ini"));
        progress += 1/size;
        if(ini.get("PoE Standalone", "active", boolean.class)){
            Runtime.getRuntime().exec("\"" + "C:\\Program Files (x86)\\Grinding Gear Games\\Path of Exile\\PathOfExile_x64Steam.exe" + "\"");
        }
        progress_bar.setProgress(progress);

        //Open tools
        ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\tools.ini"));
        for (String toolName: ini.keySet()) {
            progress += 1/size;
            if(ini.get(toolName, "active", boolean.class)){
                String type = ini.get(toolName, "type", String.class);
                String link = ini.get(toolName, "link", String.class);
                String runFileName = ini.get(toolName, "runfilename", String.class);
                boolean selfUpdates = ini.get(toolName, "selfupdates", boolean.class);

                System.out.println(toolName + " " + runFileName);

                runTool(toolName, type, link, runFileName.replace("%20", " "), selfUpdates);

            }
            progress_bar.setProgress(progress);
        }
        System.exit(0);

    }

    public void runTool(String toolName, String type, String link, String runFileName, boolean selfUpdates) throws IOException {
        Tool tool = new Tool(toolName, type, link, runFileName, selfUpdates);
        if(!tool.checkVersion()){
            tool.updateTool();
        }
        try{
            tool.runTool();
        } catch (IOException e){
            e.printStackTrace();

        }


    }


    public void populateWebsites(){
        Wini ini = null;
        try {
            ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\websites.ini"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String websiteName: ini.keySet()) {
            CheckBox check = new CheckBox();
            String websiteDescription = ini.get(websiteName, "websiteDescription", String.class);
            if(websiteDescription.isEmpty()){
                check.setText(websiteName);
            } else {
                check.setText(websiteName + " - " + websiteDescription);
            }
            Boolean selected = ini.get(websiteName, "active", boolean.class);
            check.setSelected(selected);
            check.setId(websiteName);
            Wini finalIni = ini;
            check.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    finalIni.put(websiteName, "active", true);
                    try {
                        finalIni.store();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    finalIni.put(websiteName, "active", false);
                    try {
                        finalIni.store();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
            check.setStyle("-fx-background: #AEECEF;");
            website_list.getItems().add(check);
        }

    }

    public void populateTools(){
        Wini ini = null;
        try {
            ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\tools.ini"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String toolName: ini.keySet()) {
            CheckBox check = new CheckBox();
            String toolDescription = ini.get(toolName, "toolDescription", String.class);
            if(toolDescription.isEmpty()){
                check.setText(toolName);
            } else {
                check.setText(toolName + " - " + toolDescription);
            }
            Boolean selected = ini.get(toolName, "active", boolean.class);
            check.setSelected(selected);
            check.setId(toolName);
            Wini finalIni = ini;
            check.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    finalIni.put(toolName, "active", true);
                    try {
                        finalIni.store();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    finalIni.put(toolName, "active", false);
                    try {
                        finalIni.store();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
            check.setStyle("-fx-background: #AEECEF;");
            tool_list.getItems().add(check);
        }

    }


    public void populateSettings(){
        Wini ini = null;
        try {
            ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\settings.ini"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String setting: ini.keySet()) {
            if(!setting.equals("Launcher")){
                CheckBox check = new CheckBox();
                String toolDescription = ini.get(setting, "settingDescription", String.class);
                if(toolDescription.isEmpty()){
                    check.setText(setting);
                } else {
                    check.setText(setting + " - " + toolDescription);
                }
                Boolean selected = ini.get(setting, "active", boolean.class);
                check.setSelected(selected);
                check.setId(setting);
                Wini finalIni = ini;
                check.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        finalIni.put(setting, "active", true);
                        try {
                            finalIni.store();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        finalIni.put(setting, "active", false);
                        try {
                            finalIni.store();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
                check.setStyle("-fx-background: #AEECEF;");
                settings_list.getItems().add(check);
                }
        }
    }
    public void initialize(){
        accord.setExpandedPane(launcher_pane);
        populateWebsites();
        populateTools();
        populateSettings();
    }


    public static void openWebpage(String url) {
        try {
            new ProcessBuilder("x-www-browser", url).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
