package who.is.neighbor.address.domain;

import who.is.neighbor.address.infrastructure.entity.SigunguEntity;

public record Sigungu(String sidoName, String sigunguName) {
    public static Sigungu from(SigunguEntity sigungu) {
        return new Sigungu(sigungu.getSido().getSidoName(), sigungu.getSigunguName());
    }
}
