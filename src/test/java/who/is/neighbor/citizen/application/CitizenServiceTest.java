package who.is.neighbor.citizen.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import who.is.neighbor.account.infrastructure.AccountRepository;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.infrastructure.CitizenJpaRepository;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;
import who.is.neighbor.citizen.web.request.CitizenRegistrationRequest;
import who.is.neighbor.citizen.web.response.CitizenResponse;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.infrastructure.HobbyJpaRepository;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitizenServiceTest {

    @Mock
    private CitizenJpaRepository citizenRepository;

    @Mock
    private HobbyJpaRepository hobbyJpaRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CitizenService citizenService;

    @DisplayName("사용자는 회원가입을 할 수 있습니다.")
    @Test
    void signUp() {
        UserDetails userDetails = mock(UserDetails.class);
        given(userDetails.getUsername()).willReturn("test@gmail.com");

        String nickname = "nickname";
        String phoneNumber = "010-1234-5678";

        CitizenRegistrationRequest request = new CitizenRegistrationRequest(nickname, phoneNumber, null, null);
        CitizenEntity citizenEntity = new CitizenEntity(
                1L,
                nickname,
                LocalDate.now(),
                phoneNumber,
                null,
                null);
        AccountEntity accountEntity = mock(AccountEntity.class);
        when(accountRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(accountEntity));
        when(accountEntity.isEmailVerified()).thenReturn(true);
        given(citizenRepository.save(any(CitizenEntity.class))).willReturn(citizenEntity);

        CitizenResponse actual = citizenService.save(userDetails, request);

        assertThat(actual.nickname()).isEqualTo(nickname);
        assertThat(actual.phoneNumber()).isEqualTo(phoneNumber);
    }

    @DisplayName("사용자는 취미를 여러개 선택 할 수 있습니다.")
    @Test
    void userCanChoiceHobbies() {
        Citizen citizen = new Citizen("nickname", LocalDate.now(), "010-1234-5678", null, null);
        CitizenEntity citizenEntity = new CitizenEntity(citizen);
        given(citizenRepository.findById(1L)).willReturn(Optional.of(citizenEntity));
        given(hobbyJpaRepository.save(any(HobbyEntity.class))).willReturn(new HobbyEntity(Hobby.from("요리")));
        citizenService.addHobbyToCitizen(1L, new HobbyRegistrationRequest("요리"));
        List<HobbyEntity> hobbies = citizenService.findHobbiesByCitizenId(1L);
        assertThat(hobbies).isNotEmpty();
        assertThat(hobbies).hasSize(1);
        assertThat(hobbies.get(0).getHobby()).isEqualTo("요리");
    }

}