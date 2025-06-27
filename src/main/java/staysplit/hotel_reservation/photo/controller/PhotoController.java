package staysplit.hotel_reservation.photo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.photo.service.PhotoUploadService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoUploadService photoUploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<String> uploadPhoto(@RequestParam("file") MultipartFile multipartFile,
                                         @RequestParam("entityType") String entityType,
                                         @RequestParam("entityId") Long entityId,
                                         Authentication authentication) throws IOException {

        if (multipartFile.isEmpty()) {
            return Response.error("사진 첨부 X");
        }

        String email = authentication.getName();
        photoUploadService.saveFile(multipartFile, entityType, entityId, email);
        return Response.success("사진이 성공적으로 업로드 되었습니다.");

    }

}
