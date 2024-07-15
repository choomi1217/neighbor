package who.is.neighbor.hobby.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import who.is.neighbor.citizen.application.CitizenService;
import who.is.neighbor.citizen.domain.CitizenRepository;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.infrastructure.HobbyJpaRepository;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HobbyServiceTest {

    @InjectMocks
    private HobbyService hobbyService;

    @Mock
    private HobbyJpaRepository hobbyJpaRepository;

    @DisplayName("취미 목록을 출력합니다.")
    @Test
    void allHobbies() {
        Hobby hobby = new Hobby("운동");
        HobbyEntity hobbyEntity = new HobbyEntity(1L, hobby);
        when(hobbyJpaRepository.findAll()).thenReturn(List.of(hobbyEntity));

        List<Hobby> hobbyList = hobbyService.findHobbies();

        assertThat(hobbyList).isNotEmpty();
        assertThat(hobbyList).hasSize(1);
        assertThat(hobbyList.get(0).hobby()).isEqualTo("운동");
    }

    @DisplayName("새로운 취미를 등록합니다.")
    @Test
    void addNewHobby() {
        Hobby hobby = new Hobby("운동");
        HobbyEntity hobbyEntity = new HobbyEntity(1L, hobby);
        given(hobbyJpaRepository.save(any(HobbyEntity.class))).willReturn(hobbyEntity);

        HobbyResponse response = hobbyService.save(new HobbyRegistrationRequest("운동"));

        assertThat(response.hobby()).isEqualTo("운동");
    }

    @DisplayName("등록된 취미를 삭제합니다.")
    @Test
    void deleteHobby() {
        Hobby hobby = new Hobby("운동");
        HobbyEntity hobbyEntity = new HobbyEntity(1L, hobby);
        given(hobbyJpaRepository.findById(1L)).willReturn(java.util.Optional.of(hobbyEntity));

        hobbyService.delete(1L);

        given(hobbyJpaRepository.findById(1L)).willReturn(Optional.empty());
        Optional<HobbyEntity> deletedHobbyEntity = hobbyJpaRepository.findById(1L);
        assertThat(deletedHobbyEntity).isEmpty();
    }

}