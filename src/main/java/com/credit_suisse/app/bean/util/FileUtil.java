package com.credit_suisse.app.bean.util;

import com.google.common.base.Preconditions;
import com.google.common.io.Closer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileUtil {
    private static final Log LOGGGER = LogFactory.getLog(com.credit_suisse.app.bean.util.FileUtil.class);

    public static final String XML_FILE_EXTENSION = ".xml";

    public static void mvToDir(File p_file, File p_destinationDir) throws IOException {
        String newFileName = p_destinationDir + "/" + p_file.getName();

        try {
            checkFile(p_file, false);
            checkDir(p_destinationDir, true);
            move(p_file.getPath(), newFileName);
        } catch (Exception e) {
            LOGGGER.warn(String.format("Moved file '%s' to '%s'.", p_file, newFileName));
            throw new IOException(e.getMessage());
        }

    }

    public static final void move(String p_fromPath, String p_toPath) throws FileNotFoundException, IOException {
        copy(p_fromPath, p_toPath);
        File l_fromFile = new File(p_fromPath);
        l_fromFile.delete();
    }

    public static final void copy(String p_fromPath, String p_toPath) throws FileNotFoundException, IOException {
        copy(new File(p_fromPath), new File(p_toPath));
    }

    public static void copy(File in, File out) throws IOException {
        Closer closer = Closer.create();
        if (in != null && out != null) {
            createDirIfNotExists(out.getParentFile());

            FileChannel inChannel = closer.register(new FileInputStream(in).getChannel());
            FileChannel outChannel = closer.register(new FileOutputStream(out).getChannel());
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } catch (IOException e) {
                throw e;
            } finally {
                closer.close();
            }
        }
    }

    public static File createDir(String pathToDir) throws IOException {
        Preconditions.checkNotNull(pathToDir);
        return createDirIfNotExists(new File(pathToDir));
    }

    public static File createDirIfNotExists (File dir) throws IOException {
        return checkDir(dir, true);
    }

    public static File checkDir(File dir, boolean create) throws IOException {
        if (create && !dir.exists()) {
            final boolean success = dir.mkdirs();
            if (!success)
                throw new IOException(String.format("Can't create directory '%s'.", dir));
            LOGGGER.info(String.format("Created directory '%s'.", dir));
        }

        if (!dir.isDirectory()) {
            throw new IOException(String.format("'%s' is not a directory.", dir));
        }

        if (!dir.canRead() || !dir.canWrite()) {
            throw new IOException(String.format("Can't read or write dir '%s'.", dir));
        }
        return dir;
    }

    public static File checkFile(File file, boolean create) throws IOException {
        if (create && !file.exists()) {
            boolean success = file.createNewFile();
            if (!success)
                throw new IOException(String.format("Can't create file '%s'.", file));
            LOGGGER.info(String.format("Created file '%s'.", file));
        }

        if (!file.canRead() || !file.canWrite()) {
            throw new IOException(String.format("Can't read or write file '%s'.", file));
        }

        return file;
    }

    public static void deleteFiles(String dcsArchivePath, String filePrefix) {
        File dirArchiv = new File(dcsArchivePath);
        if (existingDir(dirArchiv)) {
            File[] list = dirArchiv.listFiles();
            for (int i = 0; i < list.length; i++) {

                if (list[i].getName().startsWith(filePrefix)) {
                    boolean res = list[i].delete();
                    LOGGGER.warn("Suppression fichier " + list[i].getName() + " :" + res);
                }
            }
        }
    }

    public static void delete(final File f) throws IOException {
//        if (existingDir(f)) {
//            for (File c : f.listFiles())
//                delete(c);
//        }
//        if (!f.delete())
//            throw new FileNotFoundException("Failed to delete file: " + f);
        if(!deleteQuetly(f))
            throw new FileNotFoundException("Failed to delete file: " + f);
    }

    public static boolean deleteQuetly(final File f) throws IOException {
        if (existingDir(f)) {
            for (File c : f.listFiles())
                delete(c);
        }
        return f.delete();
    }

    public static File newDatFile(String path) {
        return new File(path + ".dat");
    }

    public static File firstFileFrom(final File folder) {
        Preconditions.checkNotNull(folder);
        for (final File fileEntry : folder.listFiles()) {
            if (existingDir(fileEntry)) {
                firstFileFrom(fileEntry);
            } else {
                return fileEntry;
            }
        }
        return null;
    }

    public static File firstFileFrom(String dirPathIfDirExists) throws IOException {
        File dir = checkDir(new File(dirPathIfDirExists), false);
        return firstFileFrom(dir);
    }

    public static boolean existingDir(File dir) {
        Preconditions.checkNotNull(dir);
        return dir.exists() && dir.isDirectory();
    }

}
