/*
 * Copyright (c) 2020 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek.common.util.fileUtils;

import megamek.common.Configuration;
import megamek.common.logging.DefaultMmLogger;
import megamek.common.logging.MMLogger;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MegaMekFile2 {
    private List<File> fileList = new ArrayList<>();
    private boolean isDirectory;
    private static MMLogger logger = DefaultMmLogger.getInstance();

    public MegaMekFile2(File parent, String child) {
        this(new File(parent, child).toString());
    }

    public MegaMekFile2(String pathName) {
        File standardVersion = new File(pathName);
        isDirectory = standardVersion.isDirectory();

        if (standardVersion.isDirectory()) {
            recursivelyAddAllFiles(standardVersion);
        } else {
            File userdataVersion = new File(Configuration.userdataDir(), pathName);
            fileList.add(userdataVersion.exists() ? userdataVersion : standardVersion);
        }
    }

    private void recursivelyAddAllFiles(File inputDirectory) {
        File[] files = inputDirectory.listFiles();

        File[] userDataFiles = new File(Configuration.userdataDir(), inputDirectory.getPath()).listFiles();
        if ((files != null) && (userDataFiles != null)) {
            // This is the primary and hardest case. We will first divide the files into files and
            // subdirectories, with userData being taken as the primary source for any files while
            // any subdirectories are listed as the standard directory
            Set<File> fileSet = new TreeSet<>(new FileComparator());
            Set<File> subdirectorySet = new TreeSet<>(new FileComparator());
            List<File> userDataSubdirectories = new ArrayList<>();

            // We start with the userData files, and save any files to the fileSet while
            // subdirectories are stored in the userDataSubdirectories to wait for further processing
            for (File file : userDataFiles) {
                if (file.isFile()) {
                    fileSet.add(file);
                } else {
                    userDataSubdirectories.add(file);
                }
            }

            // We can just add everything here to the sets, as userData has been processed already
            for (File file : fileSet) {
                if (file.isFile()) {
                    fileSet.add(file);
                } else {
                    subdirectorySet.add(file);
                }
            }

            // We've removed any duplicate files as they were added to the fileSet, so we can just
            // add all of the files to the fileList
            fileList.addAll(fileSet);

            // Now we add the userData subdirectories to the subdirectory set, to give us a list of
            // unique subdirectories
            subdirectorySet.addAll(userDataSubdirectories);

            // And finally we recursively search through the subdirectories
            for (File file : subdirectorySet) {
                recursivelyAddAllFiles(file);
            }
        } else if (files != null) {
            // This folder is only located in the standard folder path, so we can just recursively
            // add all files under it to the file list without needing to check for whether or not
            // they are in the userData directory path
            recursivelyAddAllFilesSinglePath(files);
        } else if (userDataFiles != null) {
            // This folder is only located in the userData folder path, so we can just recursively
            // add all files under it to the file list without needing to check for whether or not
            // they are in the standard directory path
            recursivelyAddAllFilesSinglePath(userDataFiles);
        }
    }

    /**
     * This recursively searches through a file tree to find all of the files located within it
     * @param files the file array to parse through
     */
    private void recursivelyAddAllFilesSinglePath(File[] files) {
        if (files != null) { //need this null check because listFile returns an array not a list
            for (File file : files) {
                // first, we need to determine if the file is zipped. If it is, we unzip

                if (file.isDirectory()) {
                    // Recursively search and add any found files to the file list
                    recursivelyAddAllFilesSinglePath(file.listFiles());
                } else {
                    // Add the file to the file list
                    fileList.add(file);
                }
            }
        }
    }

    /**
     * getFile() is used to get a single file, and should not be used for directories
     * @return the first file in the list, or null if the fileList is of size 0
     */
    public File getFile() {
        if (isDirectory) {
            logger.warning(MegaMekFile2.class, "getFile",
                    "Called getFile for a directory, " + fileList.get(0).getParent()
                            + ". Did you mean to call getFiles instead?");
        }

        if (fileList.size() > 0) {
            return fileList.get(0);
        } else {
            return null;
        }
    }

    /**
     * getFiles() is used for directories, and should not be used for single files
     * @return the fileList for the provided directory
     */
    public List<File> getFiles() {
        if (!isDirectory) {
            logger.warning(MegaMekFile2.class, "getFiles",
                    "Called getFiles for a non-directory. Did you mean to call getFile instead?");
        }
        return fileList;
    }

    /**
     * @return the paths of the files located within as a string
     */
    @Override
    public String toString() {
        if (isDirectory) {
            return fileList.toString();
        } else if (fileList.size() > 0) {
            return fileList.get(0).toString();
        } else {
            return "";
        }
    }

    /**
     * This is a custom comparator that compares if two files have the same file name. It returns 0
     * if they do, otherwise it compares the paths and returns the compared value
     */
    private static class FileComparator implements Comparator<File>, Serializable {
        private static final long serialVersionUID = 6621474836628749080L;

        @Override
        public int compare(File f1, File f2) {
            // FIXME : This will overwrite same named files that originate in different folder paths, which shouldn't happen
            if (Paths.get(f1.getPath()).getFileName().toString()
                    .equals(Paths.get(f2.getPath()).getFileName().toString())) {
                return 0;
            } else {
                return f1.getPath().compareTo(f2.getPath());
            }
        }
    }
}
