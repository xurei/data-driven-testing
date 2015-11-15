package org.jsimple.data.driven.testing.dsl;


import java.io.File;

public class Scenarios {

    public static File buildInputFolder(String testName, String scenarioName){
        return new File(
                    new File(
                            new File(Folder.RESOURCE_FOLDER, testName),
                            scenarioName),
                    Folder.INPUT_FOLDER_NAME);
    };

    public static File buildOutputResourceFolder(String testName, String scenarioName){
        return new File(
                        new File(
                                new File(Folder.RESOURCE_FOLDER, testName),
                                scenarioName),
                        Folder.OUTPUT_FOLDER_NAME);
    };

    public static File buildOutputTargetFolder(String testName, String scenarioName){
        return new File(
                    new File(
                            new File(Folder.TARGET_FOLDER, testName),
                            scenarioName),
                    Folder.OUTPUT_FOLDER_NAME);
    };

    public static File buildErrorTargetFolder(String testName, String scenarioName){
        return new File(
                    new File(
                            new File(Folder.TARGET_FOLDER, testName),
                            scenarioName),
                    Folder.ERROR_FOLDER_NAME);
    };

}
