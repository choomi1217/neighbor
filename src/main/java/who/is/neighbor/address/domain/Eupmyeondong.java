package who.is.neighbor.address.domain;

import who.is.neighbor.address.infrastructure.entity.EupmyeondongEntity;

public record Eupmyeondong(String sidoName, String sigunguName, String eupmyeondongName) {
    public static Eupmyeondong from(EupmyeondongEntity eupmyeondong) {
        return new Eupmyeondong(eupmyeondong.getSido().getSidoName(),
                eupmyeondong.getSigungu().getSigunguName(),
                eupmyeondong.getEupmyeondongName());
    }
}
