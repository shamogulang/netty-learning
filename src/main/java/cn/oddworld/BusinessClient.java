package cn.oddworld;
import java.io.IOException;
public class BusinessClient {
    public static void main(String[] args) throws IOException {
        FileServiceImpl fileService = FileServiceImpl.openFileService("oddworld", false);
        try (ReadOnlyFile ff = fileService.openFile("business.txt")) {
            String rsp = ff.readLineAndCommitPosition();
            int i = 0;
            while (rsp != null && i < 20) {
                i++;
                System.out.println(rsp);
                rsp = ff.readLineAndCommitPosition();
            }
        }
    }
}
