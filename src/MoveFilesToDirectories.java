import java.io.IOException;
import java.nio.file.*;
//创建去掉后缀的同名文件夹，并将文件移动到同名文件夹中
public class MoveFilesToDirectories {

    public static void main(String[] args) {
        // 指定要处理的目录路径
        Path startDir = Paths.get("your_directory_path_here"); // 替换为你实际的目录路径
        try {
            moveFilesToDirectories(startDir);
            System.out.println("Files have been moved to their respective directories.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void moveFilesToDirectories(Path dir) throws IOException {
        // 递归遍历目录
        Files.walk(dir)
                .filter(Files::isRegularFile) // 只选择文件
                .forEach(file -> {
                    try {
                        // 获取文件名（不带路径）
                        String fileName = file.getFileName().toString();

                        // 创建去掉后缀的文件夹名
                        String directoryName = removeSuffix(fileName);

                        // 构建新的文件夹路径
                        Path newDirectory = dir.resolve(directoryName);

                        // 如果文件夹不存在，则创建它
                        if (!Files.exists(newDirectory)) {
                            Files.createDirectory(newDirectory);
                        }

                        // 构建新文件路径
                        Path newFilePath = newDirectory.resolve(fileName);

                        // 移动文件到新的文件夹
                        Files.move(file, newFilePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved: " + file + " to " + newFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static String removeSuffix(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName; // 文件没有后缀
        } else {
            return fileName.substring(0, lastDotIndex); // 去掉后缀
        }
    }
}