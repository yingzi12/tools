import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
//文件是否有后缀或者是压缩格式，如果不是就添加新的后缀.7z
public class FileSuffixUpdater {

    public static void main(String[] args) {
        Path startDir = Paths.get("E:\\all\\樱晚gigi 写真全套合集175套（大量稀有\\未流出）"); // 替换为你的目录路径
        try {
            updateFileSuffixes(startDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateFileSuffixes(Path dir) throws IOException {
        List<Path> filesToUpdate = new ArrayList<>();

        // 递归遍历目录
        Files.walk(dir)
                .filter(Files::isRegularFile) // 只选择文件
                .forEach(file -> {
                    String fileName = file.getFileName().toString();
                    if (!hasValidSuffix(fileName)) {
                        filesToUpdate.add(file);
                    }
                });

        // 更新文件名
        for (Path file : filesToUpdate) {
            Path newFilePath = file.resolveSibling(file.getFileName() + ".7z");
            System.out.println("Renaming: " + file + " to " + newFilePath);
            Files.move(file, newFilePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static boolean hasValidSuffix(String fileName) {
        // 检查是否已经有有效的后缀（例如常见的压缩格式）
        String lowerCaseName = fileName.toLowerCase();
        return lowerCaseName.endsWith(".zip") || lowerCaseName.endsWith(".rar")
                || lowerCaseName.endsWith(".7z") || lowerCaseName.endsWith(".gz")
                || lowerCaseName.endsWith(".bz2") || lowerCaseName.endsWith(".tar")
                || lowerCaseName.endsWith(".jar") || lowerCaseName.endsWith(".war")
                || lowerCaseName.endsWith(".ear") || lowerCaseName.endsWith(".apk")
                || lowerCaseName.endsWith(".iso") || lowerCaseName.endsWith(".dmg")
                || lowerCaseName.endsWith(".cab") || lowerCaseName.endsWith(".xz");
    }
}