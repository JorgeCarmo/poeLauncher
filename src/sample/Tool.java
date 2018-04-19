package sample;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Tool {
    public String toolName;
    public String type;
    public String link;
    public String runFileName;
    public boolean selfUpdates;
    private GithubAPI git;

    public Tool(String toolName, String type, String link, String runFileName, boolean selfUpdates) throws IOException {
        this.toolName = toolName;
        this.type = type;
        this.link = link;
        this.runFileName = runFileName;
        this.selfUpdates = selfUpdates;
        git = new GithubAPI(link);
    }


    //checks the current installed version
    public boolean checkVersion() throws IOException {
        Wini ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\tools.ini"));
        String version = ini.get(toolName, "version", String.class);
        return version.equals(git.getLatestVersion());
    }

    //updates the current installed version
    public void updateVersionIni(String currentVersion) throws IOException {
        Wini ini = new Wini(new File(System.getProperty("user.dir") +"\\ini\\tools.ini"));
        ini.put(toolName, "version", currentVersion);
        ini.store();

    }

    public void updateTool() throws IOException {
        if(!selfUpdates) {
            String name = git.getFileName();
            URL download_url = git.getDownloadURL();

            ReadableByteChannel rbc = Channels.newChannel(download_url.openStream());
            String file = System.getProperty("user.dir");
            new File(file + "\\dw").mkdirs();
            FileOutputStream fos = new FileOutputStream(file + ("\\dw\\") + name);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();

            try {
                File zipDir = new File(file + ("\\dw\\") + name);
                ZipFile zipfile = new ZipFile(zipDir);
                zipfile.extractAll(file + ("\\dw\\") + toolName);
                zipDir.delete();
            } catch (ZipException e) {
                e.printStackTrace();
            }

            updateVersionIni(git.getLatestVersion());
        }
    }


    public void runTool() throws IOException {

        File file = new File(System.getProperty("user.dir") + ("\\dw\\") + toolName);
        if(!file.exists()){
            forceUpdate();
        }

        if(runFileName.substring(toString().length() - 4).equals(".exe")){
            Runtime.getRuntime().exec("\"" + System.getProperty("user.dir") + ("\\dw\\") + toolName + "\\" + runFileName + "\"");
        } else {
            Runtime.getRuntime().exec("cmd /c start " + System.getProperty("user.dir") + ("\\dw\\") + toolName + "\\" + runFileName);
        }
    }

    public void forceUpdate() throws IOException {
        String name = git.getFileName();
        URL download_url = git.getDownloadURL();

        ReadableByteChannel rbc = Channels.newChannel(download_url.openStream());
        String file = System.getProperty("user.dir");
        new File(file + "\\dw").mkdirs();
        FileOutputStream fos = new FileOutputStream(file + ("\\dw\\") + name);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();

        try {
            File zipDir = new File(file + ("\\dw\\") + name);
            ZipFile zipfile = new ZipFile(zipDir);
            zipfile.extractAll(file + ("\\dw\\") + toolName);
            zipDir.delete();
        } catch (ZipException e) {
            e.printStackTrace();
        }
        updateVersionIni(git.getLatestVersion());
    }


}
