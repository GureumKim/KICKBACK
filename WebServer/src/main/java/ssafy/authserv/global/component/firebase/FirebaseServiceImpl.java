package ssafy.authserv.global.component.firebase;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    @Value("${app.firebase-bucket}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile file, UUID memberId) throws IOException{
        if (file.isEmpty()) {
            throw new IOException("File's empty");
        }

        // 공개적으로 이미지를 볼 수 있도록 ACL 설정을 추가
        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        String fileName = memberId.toString() + "-" + "profile_image";
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setAcl(acls)
                .build();
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        storage.create(blobInfo, file.getBytes());

        // 생성된 파일의 공개 URL을 반환
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

}
