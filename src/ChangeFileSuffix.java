import java.io.IOException;
import java.nio.file.*;
//修改这些文件的后缀为.7z
public class ChangeFileSuffix {

    public static void main(String[] args) {
        // 指定要处理的目录路径
        Path startDir = Paths.get("your_directory_path_here"); // 替换为你实际的目录路径
        try {
            changeSuffixTo7z(startDir);
            System.out.println("All file suffixes have been changed to .7z.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void changeSuffixTo7z(Path dir) throws IOException {
        // 递归遍历目录
        Files.walk(dir)
                .filter(Files::isRegularFile) // 只选择文件
                .forEach(file -> {
                    try {
                        // 获取新的文件名
                        Path newFilePath = renameWithSuffix(file, ".7z");
                        if (newFilePath != null) {
                            // 如果新文件路径不为空，则移动（重命名）文件
                            Files.move(file, newFilePath, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Renamed: " + file + " to " + newFilePath);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static Path renameWithSuffix(Path path, String newSuffix) {
        // 提取文件名和父路径
        String fileName = path.getFileName().toString();
        Path parentPath = path.getParent();

        // 构造新的文件名
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            // 如果没有后缀，直接添加.7z
            return parentPath.resolve(fileName + newSuffix);
        } else {
            // 如果有后缀，替换为.7z
            String newName = fileName.substring(0, dotIndex) + newSuffix;
            return parentPath.resolve(newName);
        }
    }
}