package ssafy.authserv.global.component.firebase;
import com.google.firebase.FirebaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FirebaseService {

    String uploadImage(MultipartFile file, UUID memberId) throws IOException;

//    String uploadImage(MultipartFile file, UUID memberId, String idToken) throws IOException, FirebaseException;
}
