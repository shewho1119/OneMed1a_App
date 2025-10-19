package com.onemed1a.backend.media.user.status;

import com.onemed1a.backend.model.MediaData;
import com.onemed1a.backend.model.UserMediaStatus;
import com.onemed1a.backend.controller.UserMediaStatusController;
import com.onemed1a.backend.service.UserMediaStatusService;
import com.onemed1a.backend.dto.UserMediaStatusDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserMediaStatusController}.
 *
 * Verifies that controller endpoints correctly handle user media status
 * retrieval, creation/updating, and deletion through the underlying service layer.
 */
class UserMediaStatusControllerTest {

    private UserMediaStatusController controller;
    private UserMediaStatusService service;

    /**
     * Initializes the controller and service mocks before each test.
     */
    @BeforeEach
    void setup() {
        service = mock(UserMediaStatusService.class);
        controller = new UserMediaStatusController(service);
    }

    /**
     * Verifies that {@link UserMediaStatusController#getUserMediaByUserId(UUID, UserMediaStatus.Status, MediaData.MediaType, int, int, String)}
     * correctly delegates to the service and returns filtered results for a user.
     *
     * Ensures the response contains the expected status and that the service method is invoked once.
     */
    @Test
    void shouldReturnUserMediaByUserIdWithFiltersAndPaging() {
        UUID userId = UUID.randomUUID();

        UserMediaStatus s = UserMediaStatus.builder()
                .id(UUID.randomUUID())
                .status(UserMediaStatus.Status.WATCHING)
                .build();

        when(service.getUserMedia(userId, UserMediaStatus.Status.WATCHING, MediaData.MediaType.MOVIE))
                .thenReturn(List.of(s));

        var result = controller.getUserMediaByUserId(
                userId,
                UserMediaStatus.Status.WATCHING,
                MediaData.MediaType.MOVIE,
                0,
                10,
                "updatedAt,desc"
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(UserMediaStatus.Status.WATCHING);
        verify(service).getUserMedia(userId, UserMediaStatus.Status.WATCHING, MediaData.MediaType.MOVIE);
    }

    /**
     * Verifies that {@link UserMediaStatusController#createUserMediaStatus(UserMediaStatusDTO)}
     * correctly calls the service upsert method and returns a 200 response
     * with the updated {@link UserMediaStatus}.
     */
    @Test
    void shouldCreateOrUpdateStatus() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();

        UserMediaStatusDTO dto = new UserMediaStatusDTO(
                id,                       // use a UUID (never null)
                userId,
                mediaId,
                UserMediaStatus.Status.COMPLETED,
                5,
                "Great!"
        );

        UserMediaStatus saved = UserMediaStatus.builder()
                .id(id)
                .status(UserMediaStatus.Status.COMPLETED)
                .rating(5)
                .reviewText("Great!")
                .build();

        when(service.upsert(dto)).thenReturn(ResponseEntity.ok(saved));

        var response = controller.createUserMediaStatus(dto);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getStatus()).isEqualTo(UserMediaStatus.Status.COMPLETED);
        assertThat(response.getBody().getRating()).isEqualTo(5);

        verify(service).upsert(dto);
    }

    /**
     * Verifies that {@link UserMediaStatusController#deleteUserMediaStatus(UUID)}
     * delegates correctly to {@link UserMediaStatusService#delete(UUID)} and
     * returns a successful response containing the deleted status ID.
     */
    @Test
    void shouldDeleteStatusAndReturnId() {
        UUID statusId = UUID.randomUUID();
        when(service.delete(statusId)).thenReturn(true);

        var response = controller.deleteUserMediaStatus(statusId);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(statusId);
        verify(service).delete(statusId);
    }
}