import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String fullpath, GameProgress gameToSave) {
        try (FileOutputStream fos = new FileOutputStream(fullpath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameToSave);
        } catch (Exception ex) {
            System.out.println(ex.getMessage(
            ));
        }
    }

    public static void zipFiles(String arcPath, List<File> filesToArc) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(arcPath))) {
            for (File file : filesToArc) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    zout.putNextEntry(new ZipEntry(file.getName()));
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(45, 16, 8, 2566);
        GameProgress game2 = new GameProgress(90, 4, 1, 105);
        GameProgress game3 = new GameProgress(6, 98, 81, 18354);

        saveGame("D://Games/savegames/save1.dat", game1);
        saveGame("D://Games/savegames/save2.dat", game2);
        saveGame("D://Games/savegames/save3.dat", game3);

        List<File> inputFiles = new ArrayList<>();
        File dir = new File("D://Games/savegames");
// если объект представляет каталог
        if (dir.isDirectory()) {
// получаем все вложенные объекты в каталоге
            for (File item : dir.listFiles()) {
// если файл, добавляем
                if (!item.isDirectory()) {
                    inputFiles.add(item);
                }
            }
        }

        zipFiles("D://Games/savegames/saves.zip", inputFiles);

        for (File f : inputFiles) {
            f.delete();
        }
    }
}

