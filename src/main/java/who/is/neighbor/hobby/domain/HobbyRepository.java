package who.is.neighbor.hobby.domain;

import who.is.neighbor.hobby.web.response.HobbyResponse;

public interface HobbyRepository {
    HobbyResponse save(Hobby hobby);
}
